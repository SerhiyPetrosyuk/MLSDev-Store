package com.mlsdev.mlsdevstore.presentaion.store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.model.item.ListItem
import com.mlsdev.mlsdevstore.data.model.item.SearchResult
import com.mlsdev.mlsdevstore.databinding.ItemFooterCategoryBinding
import com.mlsdev.mlsdevstore.databinding.ItemHeaderCategoryBinding
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseHeaderViewModel

class RandomProductsAdapter : ProductsAdapter() {

    private var onFooterClickListener: View.OnClickListener? = null
    private var onAllCategoriesListener: View.OnClickListener? = null
    private var onAuthorizeListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListItem> {
        return when (viewType) {
            VIEW_TYPE_HEADER -> CategoryNameHeaderViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_header_category,
                            parent,
                            false))
            VIEW_TYPE_FOOTER -> ReadMoreInCategoryFooter(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_footer_category,
                            parent,
                            false))
            else -> return super.onCreateViewHolder(parent, viewType)
        }
    }

    inner class CategoryNameHeaderViewHolder(private val binding: ItemHeaderCategoryBinding) : BaseViewHolder<ListItem>(binding.root) {

        override fun bindView(item: ListItem) {
            if (binding.viewModel == null)
                binding.viewModel = BaseHeaderViewModel()

            binding.viewModel?.setHeader(item.title)
            binding.btnAllCategories.setOnClickListener(onAllCategoriesListener)
            binding.btnSignIn.setOnClickListener(onAuthorizeListener)
            binding.btnSignUp.setOnClickListener(onAuthorizeListener)
        }
    }

    inner class ReadMoreInCategoryFooter(private val binding: ItemFooterCategoryBinding) : BaseViewHolder<ListItem>(binding.root) {

        init {
            this.binding.btnGetMoreItems.setOnClickListener(onFooterClickListener)
        }

        override fun bindView(item: ListItem) {
            if (binding.viewModel == null)
                binding.viewModel = BaseHeaderViewModel()

            binding.viewModel?.setHeader(item.title)
        }
    }

    override fun setData(searchResult: SearchResult) {
        items.clear()
        items.addAll(searchResult.itemSummaries)

        if (searchResult.refinement != null
                && !searchResult.refinement.categoryDistributions.isEmpty()
                && !items.isEmpty()) {
            val title = searchResult.refinement.categoryDistributions[0].categoryName
            val headerAndFooter = Item(title)
            items.add(0, headerAndFooter)
            items.add(headerAndFooter)
            withHeader = true
            withFooter = true
        }
    }

    override fun setOnClickListeners(vararg listeners: View.OnClickListener) {

        if (listeners.isNotEmpty())
            onAuthorizeListener = listeners[0]

        if (listeners.size > 1)
            onAllCategoriesListener = listeners[1]

        if (listeners.size > 2)
            onFooterClickListener = listeners[2]

    }
}
