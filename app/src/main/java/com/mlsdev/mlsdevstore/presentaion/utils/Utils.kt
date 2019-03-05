package com.mlsdev.mlsdevstore.presentaion.utils

import android.content.Context
import android.net.ConnectivityManager
import com.mlsdev.mlsdevstore.data.model.product.Price

open class Utils(private val context: Context) {

    open fun isNetworkAvailable(): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    fun formatProductPrice(price: Price): Array<String> =
            price.value.toString().split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

}