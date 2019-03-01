package com.mlsdev.mlsdevstore.presentaion.categories

import androidx.lifecycle.MutableLiveData
import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import javax.inject.Inject

class CategoriesViewModel @Inject
constructor(
        private val dataSource: DataSource,
        private val utils: Utils
) : BaseViewModel() {

    val categories = MutableLiveData<List<CategoryTreeNode>>()

    fun getRootCategories() {
        checkNetworkConnection(utils) {
            setIsLoading(true)
            compositeDisposable.add(dataSource.loadRootCategoryTree().subscribe(
                    {
                        setIsLoading(false)
                        categories.postValue(it.categoryTreeNode.childCategoryTreeNodes)
                    },
                    { handleError(it) }))
        }
    }
}
