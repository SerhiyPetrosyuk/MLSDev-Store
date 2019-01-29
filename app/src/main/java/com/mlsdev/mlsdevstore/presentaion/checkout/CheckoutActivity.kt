package com.mlsdev.mlsdevstore.presentaion.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.ActivityCheckoutBinding
import com.mlsdev.mlsdevstore.presentaion.BaseActivity

class CheckoutActivity : BaseActivity() {
    lateinit var binding: ActivityCheckoutBinding
    lateinit var checkoutViewModel: CheckoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout)
        init()
    }

    private fun init() {
        checkoutViewModel = ViewModelProviders.of(this, viewModelFactory).get(CheckoutViewModel::class.java)
        binding.viewModel = checkoutViewModel

        errorInViewHandler.observeAuthError(this, checkoutViewModel.authErrorLiveData)
        errorInViewHandler.observeNetworkError(this, checkoutViewModel.networkErrorLiveData)
        errorInViewHandler.observeCommonError(this, checkoutViewModel.commonErrorLiveData)
        errorInViewHandler.observeTechError(this, checkoutViewModel.technicalErrorLiveData)
        checkoutViewModel.personalInfoErrorEvent.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        initToolbar(binding.toolbar)
        displayBackArrow(true)
    }

    companion object {

        fun launch(context: Context) {
            context.startActivity(Intent(context, CheckoutActivity::class.java))
        }
    }
}
