package com.mlsdev.mlsdevstore.presentaion.products

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.DataLoadState
import com.mlsdev.mlsdevstore.data.local.database.Column.Companion.CATEGORY_NAME
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
        initTransitionName()
        initCollectionView()
    }

    private fun initTransitionName() {
        intent.getStringExtra(CATEGORY_NAME)?.let { categoryName ->
            binding.textTitle.transitionName = categoryName
        }
    }

    private fun initCollectionView() {
        productsAdapter = PagedProductsAdapter(
                { viewModel.retry() },
                { product ->
                    Log.d("ON_PRODUCT_ITEM_CLICK", "Product id: ${product.id}; Product name: ${product.title}")
                })
        binding.rvProducts.adapter = productsAdapter
        binding.refreshLayout.setOnRefreshListener { viewModel.refresh() }

        (binding.rvProducts.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = productsAdapter.getItemViewType(position)
                return when (viewType) {
                    R.layout.layout_network_state -> 2
                    R.layout.item_product -> 1
                    else -> -1
                }
            }
        }

        viewModel.loadingState.observe(this, Observer {
            productsAdapter.setLoadState(it)

            when (it) {
                DataLoadState.LOADING -> binding.refreshLayout.isRefreshing = binding.refreshLayout.isRefreshing
                else -> binding.refreshLayout.isRefreshing = false
            }

        })

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

    override fun onBackPressed() {
        binding.textTitle.setTextColor(Color.BLACK)
        super.onBackPressed()
    }
}