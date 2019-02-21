package com.mlsdev.mlsdevstore.presentaion.utils

import androidx.navigation.*
import com.mlsdev.mlsdevstore.R
import com.roughike.bottombar.BottomBar

val navOptions: NavOptions = navOptions {
    this.launchSingleTop = true
    this.popUpTo = R.id.app_nav_host
}

class NavUtils {
    companion object {

        fun setupWithNavController(bottomBar: BottomBar, navController: NavController) {

            bottomBar.setOnTabSelectListener {
                when (it) {
                    R.id.navigation_item_cart -> navigateToCart(navController)
                    R.id.navigation_item_account -> navigateToAccoutn(navController)
                    else -> navigateToStore(navController)
                }
            }
        }

        fun navigateToStore(navController: NavController) {
            navController.navigate(R.id.store_flow_fragment, null, navOptions)
        }

        fun navigateToCart(navController: NavController) {
            navController.navigate(R.id.cart_flow_fragment, null, navOptions)
        }

        fun navigateToAccoutn(navController: NavController) {
            navController.navigate(R.id.account_flow_fragment, null, navOptions)
        }
    }
}