package com.mlsdev.mlsdevstore.presentaion.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.model.item.ListItem
import com.mlsdev.mlsdevstore.databinding.ItemCartProductBinding
import com.mlsdev.mlsdevstore.databinding.ItemOrderTotalBinding
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder
import com.mlsdev.mlsdevstore.presentaion.store.*

class ItemsAdapter(
        val removeItemFromCartListener: (productId: String) -> Unit
) : ProductsAdapter() {
    init {
        withFooter = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListItem> {
        return if (viewType == VIEW_TYPE_ITEM)
            ProductViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_cart_product,
                            parent,
                            false))
        else
            TotalSumViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_order_total,
                            parent,
                            false
                    ))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ListItem>, position: Int) {
        if (position < items.size)
            super.onBindViewHolder(holder, position)
        else
            holder.bindView(null)
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty())
            super.getItemCount()
        else
            items.size + HEADER_OR_FOOTER
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size)
            VIEW_TYPE_FOOTER
        else
            VIEW_TYPE_ITEM
    }

//    override fun onItemCountChanged(count: Int) {
//        if (!items.isEmpty())
//            return
//
//        items.clear()
//        items.addAll(cart.items)
//        notifyDataSetChanged()
//    }
//
//    override fun onItemRemoved(itemId: String) {
//        var removedItemPosition = -1
//
//        for (item in items)
//            if (item.id == itemId)
//                removedItemPosition = items.indexOf(item)
//
//        items.removeAt(removedItemPosition)
//        notifyItemRemoved(removedItemPosition)
//        notifyItemChanged(items.size)
//    }

    inner class ProductViewHolder(private val binding: ItemCartProductBinding) : BaseViewHolder<ListItem>(binding.root) {

        override fun bindView(item: ListItem?) {
            if (binding.viewModel == null)
                binding.viewModel = ProductItemViewModel(removeFromCartListener)

            if (binding.viewModel != null)
                binding.viewModel?.setItem(item!!)
        }
    }

    inner class TotalSumViewHolder(private val binding: ItemOrderTotalBinding) : BaseViewHolder<ListItem>(binding.root) {

        override fun bindView(item: ListItem?) {
//            if (binding.viewModel == null)
//                binding.viewModel = TotalSumItemViewModel(cart.getTotalSum())
//            else
//                binding.viewModel?.setTotalSum(cart.getTotalSum())
        }
    }

}
