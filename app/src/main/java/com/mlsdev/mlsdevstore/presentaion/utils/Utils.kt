package com.mlsdev.mlsdevstore.presentaion.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Utils(private val context: Context) {

    val isNetworkAvailable: Boolean
        get() {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

}
