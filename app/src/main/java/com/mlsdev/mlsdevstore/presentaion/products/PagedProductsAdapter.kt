package com.mlsdev.mlsdevstore.presentaion.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.model.item.ListItem
import com.mlsdev.mlsdevstore.databinding.ItemProductBinding
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder
import com.mlsdev.mlsdevstore.presentaion.store.ProductItemViewModel

class PagedProductsAdapter(
        val onItemClickListener: (item: Item) -> Unit
) : PagedListAdapter<Item, PagedProductsAdapter.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder<Item>(itemView) {
        val binding = ItemProductBinding.bind(itemView)

        override fun bindView(item: Item?) {
            val viewModel = ProductItemViewModel()
            viewModel.setItem(null, item as ListItem)
            binding.viewModel = viewModel
            binding.root.setOnClickListener { onItemClickListener(item!!) }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                    oldItem.brand == newItem.brand &&
                            oldItem.description == newItem.description &&
                            oldItem.price.value == newItem.price.value &&
                            oldItem.title == newItem.title
        }
    }
}