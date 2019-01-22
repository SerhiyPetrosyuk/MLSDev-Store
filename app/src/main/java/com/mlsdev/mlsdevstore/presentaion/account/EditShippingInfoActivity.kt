package com.mlsdev.mlsdevstore.presentaion.account

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.ActivityEditShippingInfoBinding
import com.mlsdev.mlsdevstore.presentaion.BaseActivity

class EditShippingInfoActivity : BaseActivity() {
    lateinit var binding: ActivityEditShippingInfoBinding
    lateinit var viewModel: EditAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_shipping_info)
        initViewModel()
        initToolbar(binding.toolbar)
        displayBackArrow(true)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditAccountViewModel::class.java)
        binding.viewModel = viewModel
        lifecycle.addObserver(viewModel)
    }

}