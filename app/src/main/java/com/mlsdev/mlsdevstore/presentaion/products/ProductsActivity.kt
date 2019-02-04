package com.mlsdev.mlsdevstore.presentaion.products

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.presentaion.BaseActivity

class ProductsActivity : BaseActivity() {
    lateinit var viewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductsViewModel::class.java)
    }

}