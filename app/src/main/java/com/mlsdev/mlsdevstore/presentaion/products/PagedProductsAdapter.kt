package com.mlsdev.mlsdevstore.presentaion.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.model.item.ListItem
import com.mlsdev.mlsdevstore.databinding.ItemProductBinding
import com.mlsdev.mlsdevstore.presentaion.adapter.BasePagedAdapter
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder
import com.mlsdev.mlsdevstore.presentaion.store.ProductItemViewModel

class PagedProductsAdapter(
        retryCallback: () -> Unit,
        val onItemClickListener: (item: Item) -> Unit
) : BasePagedAdapter<Item>(retryCallback, diffCallback) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.layout_network_state -> (holder as BasePagedAdapter<*>.NetworkStateViewHolder).bindTo()
            R.layout.item_product -> (holder as ViewHolder).bindView(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        R.layout.layout_network_state -> NetworkStateViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_network_state, parent, false), retryCallback)
        R.layout.item_product -> ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false))
        else -> throw Exception("Unknown item view type")
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder<Item>(itemView) {
        val binding = ItemProductBinding.bind(itemView)

        override fun bindView(item: Item?) {
            val viewModel = ProductItemViewModel()
            viewModel.setItem(null, item as ListItem)
            binding.viewModel = viewModel
            binding.root.setOnClickListener { onItemClickListener(item) }
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        (hasExtraRow() && position == itemCount - 1) -> R.layout.layout_network_state
        getItem(position) != null -> R.layout.item_product
        else -> throw Exception("Unknown item view type")
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