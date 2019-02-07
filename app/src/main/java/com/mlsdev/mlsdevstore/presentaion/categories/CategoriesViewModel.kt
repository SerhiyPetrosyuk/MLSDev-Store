package com.mlsdev.mlsdevstore.presentaion.categories

import androidx.lifecycle.MutableLiveData
import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import javax.inject.Inject

class CategoriesViewModel @Inject
constructor(dataSource: DataSource, utils: Utils) : BaseViewModel() {
    val categories = MutableLiveData<List<CategoryTreeNode>>()

    init {
        this.dataSource = dataSource
        this.utils = utils
    }

    fun getRootCategories() {
        checkNetworkConnection(utils!!) {
            setIsLoading(true)
            compositeDisposable.add(dataSource!!.loadRootCategoryTree().subscribe(
                    {
                        setIsLoading(false)
                        categories.postValue(it.categoryTreeNode.childCategoryTreeNodes)
                    },
                    { handleError(it) }))
        }
    }
}
