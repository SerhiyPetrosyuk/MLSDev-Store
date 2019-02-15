package com.mlsdev.mlsdevstore.presentaion.categories

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.ActivityCategoriesBinding
import com.mlsdev.mlsdevstore.presentaion.BaseActivity
import com.mlsdev.mlsdevstore.presentaion.products.ProductsActivity
import com.mlsdev.mlsdevstore.presentaion.store.CategoriesAdapter
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys.Companion.KEY_CATEGORY_ID
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys.Companion.KEY_CATEGORY_NAME

class CategoriesActivity : BaseActivity() {
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var binding: ActivityCategoriesBinding
    lateinit var viewModel: CategoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_categories)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoriesViewModel::class.java)
        binding.viewModel = viewModel
        initRecyclerView()
        initToolbar(binding.toolbar)
        displayBackArrow(true)

        errorInViewHandler.observeAuthError(this, viewModel.authErrorLiveData)
        errorInViewHandler.observeCommonError(this, viewModel.commonErrorLiveData)
        errorInViewHandler.observeTechError(this, viewModel.technicalErrorLiveData)
        errorInViewHandler.observeNetworkError(this, viewModel.networkErrorLiveData)

        viewModel.getRootCategories()
    }

    private fun initRecyclerView() {
        categoriesAdapter = CategoriesAdapter { category, itemView ->
            Log.d("ON_ITEM_CLICK", "Category id: ${category.category.categoryId}; " +
                    "Category name: ${category.category.categoryName}")

            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    itemView,
                    ViewCompat.getTransitionName(itemView)
            )
            val intent = Intent(this, ProductsActivity::class.java).apply {
                putExtra(KEY_CATEGORY_ID, category.category.categoryId)
                putExtra(KEY_CATEGORY_NAME, category.category.categoryName)
            }
            startActivity(intent, activityOptions.toBundle())
        }
        binding.rvCategories.adapter = categoriesAdapter
        viewModel.categories.observe(this, Observer {
            categoriesAdapter.setData(it)
            binding.rvCategories.notifyDataSetChanged()
        })
    }

}