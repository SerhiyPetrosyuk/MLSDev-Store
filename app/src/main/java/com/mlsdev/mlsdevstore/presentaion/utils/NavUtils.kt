package com.mlsdev.mlsdevstore.presentaion.utils

import androidx.navigation.*
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.presentaion.account.AccountFragmentDirections
import com.mlsdev.mlsdevstore.presentaion.cart.CartFragmentDirections
import com.mlsdev.mlsdevstore.presentaion.store.StoreFragmentDirections
import com.roughike.bottombar.BottomBar

class NavUtils {
    companion object {
        fun setupWithNavController(bottomBar: BottomBar, navController: NavController) {

            val navOptions: NavOptions = navOptions {
                this.launchSingleTop = true
                this.popUpTo = R.id.nav_host
            }

            bottomBar.setOnTabSelectListener {
                when (it) {
                    R.id.navigation_item_cart -> navController.navigate(R.id.cart_flow_fragment, null, navOptions)
                    R.id.navigation_item_account -> navController.navigate(R.id.account_flow_fragment, null, navOptions)
                    else -> navController.navigate(R.id.store_flow_fragment, null, navOptions)
                }
            }
        }
    }
}