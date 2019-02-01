package com.mlsdev.mlsdevstore.presentaion.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.ActivityCategoriesBinding
import com.mlsdev.mlsdevstore.presentaion.BaseActivity
import com.mlsdev.mlsdevstore.presentaion.store.CategoriesAdapter

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
        categoriesAdapter = CategoriesAdapter { category ->
            Log.d("ON_ITEM_CLICK", "Category id: ${category.id}")
        }
        binding.rvCategories.adapter = categoriesAdapter
        viewModel.categories.observe(this, Observer {
            categoriesAdapter.setData(it)
            binding.rvCategories.notifyDataSetChanged()
        })
    }

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, CategoriesActivity::class.java)
            context.startActivity(intent)
        }
    }
}
