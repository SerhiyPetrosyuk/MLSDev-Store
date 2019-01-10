package com.mlsdev.mlsdevstore.presentaion.cart

import android.view.LayoutInflater
import android.view.ViewGroup

import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.data.model.item.ListItem
import com.mlsdev.mlsdevstore.databinding.ItemCartProductBinding
import com.mlsdev.mlsdevstore.databinding.ItemOrderTotalBinding
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder
import com.mlsdev.mlsdevstore.presentaion.store.ProductItemViewModel
import com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapter

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

import com.mlsdev.mlsdevstore.presentaion.store.HEADER_OR_FOOTER
import com.mlsdev.mlsdevstore.presentaion.store.VIEW_TYPE_FOOTER
import com.mlsdev.mlsdevstore.presentaion.store.VIEW_TYPE_ITEM

class ItemsAdapter : ProductsAdapter(), Cart.OnItemCountChangeListener, Cart.OnItemRemovedListener {
    init {
        withFooter = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        cart.addOnItemCountChangeListener(this)
        cart.addOnItemRemovedListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        cart.removeOnItemCountChangeListener(this)
        cart.removeOnItemRemovedListener(this)
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

    override fun onItemCountChanged(count: Int) {
        if (!items.isEmpty())
            return

        items.clear()
        items.addAll(cart.items)
        notifyDataSetChanged()
    }

    override fun onItemRemoved(itemId: String) {
        var removedItemPosition = -1

        for (item in items)
            if (item.id == itemId)
                removedItemPosition = items.indexOf(item)

        items.removeAt(removedItemPosition)
        notifyItemRemoved(removedItemPosition)
        notifyItemChanged(items.size)
    }

    inner class ProductViewHolder(private val binding: ItemCartProductBinding) : BaseViewHolder<ListItem>(binding.root) {

        override fun bindView(item: ListItem?) {
            if (binding.viewModel == null)
                binding.viewModel = ProductItemViewModel()

            if (binding.viewModel != null)
                binding.viewModel?.setItem(cart, item)
        }
    }

    inner class TotalSumViewHolder(private val binding: ItemOrderTotalBinding) : BaseViewHolder<ListItem>(binding.root) {

        override fun bindView(item: ListItem?) {
            if (binding.viewModel == null)
                binding.viewModel = TotalSumItemViewModel(cart.totalSum)
            else
                binding.viewModel?.setTotalSum(cart.totalSum)
        }
    }

}
