package com.mlsdev.mlsdevstore.presentaion.utils

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.mlsdev.mlsdevstore.R
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
                    R.id.navigation_item_cart -> navController.navigate(R.id.cart_fragment, null, navOptions)
                    R.id.navigation_item_account -> navController.navigate(R.id.account_fragment, null, navOptions)
                    else -> navController.navigate(R.id.store_fragment, null, navOptions)
                }
            }
        }
    }
}