package com.mlsdev.mlsdevstore.presentaion.bottom_navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.ActivityMainBinding
import com.mlsdev.mlsdevstore.presentaion.BaseActivity
import com.mlsdev.mlsdevstore.presentaion.account.AccountFragment
import com.mlsdev.mlsdevstore.presentaion.cart.CartFragment
import com.mlsdev.mlsdevstore.presentaion.store.StoreFragment
import com.roughike.bottombar.OnTabReselectListener
import com.roughike.bottombar.OnTabSelectListener

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navigationController: BottomNavigationController
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
        navigationController = BottomNavigationController()
        binding.bottomBar.setOnTabSelectListener(navigationController)
        binding.bottomBar.setOnTabReselectListener(navigationController)
    }

    inner class BottomNavigationController : OnTabSelectListener, OnTabReselectListener {

        override fun onTabReSelected(tabId: Int) {

        }

        override fun onTabSelected(tabId: Int) {
            val fragment: Fragment = when (tabId) {
                R.id.navigation_item_cart -> CartFragment()
                R.id.navigation_item_account -> AccountFragment()
                else -> StoreFragment()
            }

            replaceFragment(fragment)
        }

        private fun replaceFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.content, fragment)
                    .commit()
        }

    }
}
