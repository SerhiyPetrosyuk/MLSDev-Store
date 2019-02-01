package com.mlsdev.mlsdevstore.presentaion.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.databinding.ItemCategoryBinding
import java.util.*

class CategoriesAdapter(val onItemClick: (item: CategoryTreeNode) -> Unit) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private val categoryTreeNodes = ArrayList<CategoryTreeNode>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemCategoryBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_category,
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView()
    }

    override fun getItemCount(): Int {
        return categoryTreeNodes.size
    }

    inner class ViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBindView() {
            val node = categoryTreeNodes[adapterPosition]

            if (binding.viewModel == null)
                binding.viewModel = CategoryItemViewModel(onItemClick)

            binding.viewModel?.setData(node)
        }
    }

    fun setData(categoryTreeNodes: List<CategoryTreeNode>) {
        this.categoryTreeNodes.clear()
        this.categoryTreeNodes.addAll(categoryTreeNodes)
    }

}
