package com.mlsdev.mlsdevstore.presentaion.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.model.item.ListItem
import com.mlsdev.mlsdevstore.databinding.ItemCartProductBinding
import com.mlsdev.mlsdevstore.databinding.ItemOrderTotalBinding
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder
import com.mlsdev.mlsdevstore.presentaion.store.ProductItemViewModel
import com.mlsdev.mlsdevstore.presentaion.store.VIEW_TYPE_FOOTER
import com.mlsdev.mlsdevstore.presentaion.store.VIEW_TYPE_ITEM

class ItemsAdapter(
        val removeItemFromCartListener: (productId: String) -> Unit
) : PagedListAdapter<ListItem, BaseViewHolder<ListItem>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListItem> {
        return if (viewType == VIEW_TYPE_ITEM)
            ProductViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.item_cart_product,
                    parent,
                    false))
        else
            TotalSumViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.item_order_total,
                    parent,
                    false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ListItem>, position: Int) {
        holder.bindView(getItem(position))
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) VIEW_TYPE_FOOTER
        else VIEW_TYPE_ITEM
    }

    inner class ProductViewHolder(itemView: View) : BaseViewHolder<ListItem>(itemView) {
        private val binding = ItemCartProductBinding.bind(itemView)

        override fun bindView(item: ListItem?) {
            if (binding.viewModel == null)
                binding.viewModel = ProductItemViewModel(removeItemFromCartListener)

            if (binding.viewModel != null)
                binding.viewModel?.setItem(item!!)
        }
    }

    inner class TotalSumViewHolder(itemView: View) : BaseViewHolder<ListItem>(itemView) {
        private val binding = ItemOrderTotalBinding.bind(itemView)

        override fun bindView(item: ListItem?) {
            if (binding.viewModel == null)
                binding.viewModel = TotalSumItemViewModel()

            binding.viewModel?.setTotalSum(item?.price?.value ?: 0.0)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                    oldItem.id == newItem.id &&
                            oldItem.price.value == newItem.price.value &&
                            oldItem.title == newItem.title
        }
    }

}
