package com.mlsdev.mlsdevstore.presentaion.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.model.product.ListItem
import com.mlsdev.mlsdevstore.data.model.product.Product
import com.mlsdev.mlsdevstore.databinding.ItemProductBinding
import com.mlsdev.mlsdevstore.presentaion.adapter.BasePagedAdapter
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder
import com.mlsdev.mlsdevstore.presentaion.store.ProductItemViewModel

class PagedProductsAdapter(
        retryCallback: () -> Unit,
        val onItemClickListener: (product: Product) -> Unit
) : BasePagedAdapter<Product>(retryCallback, diffCallback) {
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

    inner class ViewHolder(itemView: View) : BaseViewHolder<Product>(itemView) {
        val binding = ItemProductBinding.bind(itemView)

        override fun bindView(item: Product?) {
            val viewModel = ProductItemViewModel(null)
            viewModel.setItem(item as ListItem)
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
        val diffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldProduct: Product, newProduct: Product): Boolean =
                    oldProduct.id == newProduct.id

            override fun areContentsTheSame(oldProduct: Product, newProduct: Product): Boolean =
                    oldProduct.brand == newProduct.brand &&
                            oldProduct.description == newProduct.description &&
                            oldProduct.itemPrice.value == newProduct.itemPrice.value &&
                            oldProduct.itemTitle == newProduct.itemTitle
        }
    }
}