package com.mlsdev.mlsdevstore.presentaion.products

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.ActivityProductsBinding
import com.mlsdev.mlsdevstore.presentaion.BaseActivity
import io.reactivex.disposables.Disposable

class ProductsActivity : BaseActivity() {
    lateinit var viewModel: ProductsViewModel
    lateinit var binding: ActivityProductsBinding
    lateinit var productsAdapter: PagedProductsAdapter
    private var collectionSubscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductsViewModel::class.java)
        viewModel.initCategoryData(intent.extras)
        binding.viewModel = viewModel
        initToolbar(binding.toolbar)
        displayBackArrow(true)
        initCollectionView()
    }

    private fun initCollectionView() {
        productsAdapter = PagedProductsAdapter { product ->
            Log.d("ON_PRODUCT_ITEM_CLICK", "Product id: ${product.id}; Product name: ${product.title}")
        }
        binding.rvProducts.adapter = productsAdapter
    }

    override fun onStart() {
        super.onStart()
        collectionSubscription = viewModel.products.subscribe { pagedList ->
            productsAdapter.submitList(pagedList)
        }
    }

    override fun onStop() {
        super.onStop()
        collectionSubscription?.dispose()
    }

}