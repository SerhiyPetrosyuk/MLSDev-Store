package com.mlsdev.mlsdevstore.presentaion.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.presentaion.BaseActivity
import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity

class SplashScreenActivity : BaseActivity() {
    lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel::class.java)

        errorInViewHandler.observeAuthError(this, viewModel.authErrorLiveData)
        errorInViewHandler.observeNetworkError(this, viewModel.networkErrorLiveData)
        errorInViewHandler.observeCommonError(this, viewModel.commonErrorLiveData)
        errorInViewHandler.observeTechError(this, viewModel.technicalErrorLiveData)
        errorInViewHandler.setCloseAppAfterError(true)

        viewModel.appAccessTokenLiveData.observe(this, Observer {
            if (it) {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                this@SplashScreenActivity.finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkAuthentication()
    }

}