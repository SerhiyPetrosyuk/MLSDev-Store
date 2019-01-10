package com.mlsdev.mlsdevstore.presentaion.store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.data.model.item.ListItem
import com.mlsdev.mlsdevstore.data.model.item.SearchResult
import com.mlsdev.mlsdevstore.databinding.ItemProductBinding
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder
import java.util.*
import javax.inject.Inject

const val VIEW_TYPE_HEADER = 0
const val VIEW_TYPE_FOOTER = 1
const val VIEW_TYPE_ITEM = 2
const val HEADER_OR_FOOTER = 1

open class ProductsAdapter @Inject constructor() : RecyclerView.Adapter<BaseViewHolder<ListItem>>(), LifecycleObserver {
    lateinit var cart: Cart
    protected var withHeader = false
    protected var withFooter = false
    protected val items: MutableList<ListItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListItem> {
        return ProductViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_product,
                        parent,
                        false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ListItem>, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (withHeader && position == 0)
            VIEW_TYPE_HEADER
        else if (withFooter && position == items.size - HEADER_OR_FOOTER)
            VIEW_TYPE_FOOTER
        else
            VIEW_TYPE_ITEM
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : BaseViewHolder<ListItem>(binding.root) {

        override fun bindView(item: ListItem?) {
            if (binding.viewModel == null)
                binding.viewModel = ProductItemViewModel()

            binding.viewModel?.setItem(cart, item!!)
        }
    }

    open fun setData(searchResult: SearchResult) {
        items.clear()
        items.addAll(searchResult.itemSummaries)
    }

    open fun setOnClickListeners(vararg listeners: View.OnClickListener) {

    }

}
