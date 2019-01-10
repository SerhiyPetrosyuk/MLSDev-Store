package com.mlsdev.mlsdevstore.presentaion.account

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import android.os.Bundle

import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.ActivityEditPersonalInfoBinding
import com.mlsdev.mlsdevstore.presentaion.BaseActivity
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean

class EditPersonalInfoActivity : BaseActivity() {
    lateinit var binding: ActivityEditPersonalInfoBinding
    lateinit var viewModel: EditAccountViewModel

    private val accountUpdatedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable, i: Int) {
            if (observable is CustomObservableBoolean && observable.get())
                finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_personal_info)
        initViewModel()
        initToolbar(binding.toolbar)
        displayBackArrow(true)
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        viewModel.accountUpdated.removeOnPropertyChangedCallback(accountUpdatedCallback)
        super.onDestroy()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditAccountViewModel::class.java)
        viewModel.accountUpdated.addOnPropertyChangedCallback(accountUpdatedCallback)
        binding.viewModel = viewModel
        lifecycle.addObserver(viewModel)
    }
}
