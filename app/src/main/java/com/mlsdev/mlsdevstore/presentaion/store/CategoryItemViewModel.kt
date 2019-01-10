package com.mlsdev.mlsdevstore.presentaion.store

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseItemViewModel

class CategoryItemViewModel : BaseItemViewModel<CategoryTreeNode>() {
    private var categoryTreeNode: CategoryTreeNode? = null
    var categoryName = ObservableField<String>()
    var leafNode = ObservableBoolean()

    override fun setData(data: CategoryTreeNode) {
        categoryTreeNode = data
        categoryName.set(categoryTreeNode!!.category.categoryName)
        leafNode.set(categoryTreeNode!!.isLeafCategoryTreeNode)
        leafNode.notifyChange()
    }
}
