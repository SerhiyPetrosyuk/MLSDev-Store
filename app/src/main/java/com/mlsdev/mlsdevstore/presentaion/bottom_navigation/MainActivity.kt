package com.mlsdev.mlsdevstore.presentaion.bottom_navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.ActivityMainBinding
import com.mlsdev.mlsdevstore.presentaion.BaseActivity

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: BottomNavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BottomNavigationViewModel::class.java)
        binding.bottomViewModel = viewModel
        lifecycle.addObserver(viewModel)
        initBottomNavigation()
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    override fun setToolBarTitle(title: Int) {
        binding.title = getString(title)
    }

    fun selectTab(@IdRes tabId: Int) {
        binding.bottomBar.selectTabWithId(tabId)
    }

    private fun initBottomNavigation() {
    }
}
