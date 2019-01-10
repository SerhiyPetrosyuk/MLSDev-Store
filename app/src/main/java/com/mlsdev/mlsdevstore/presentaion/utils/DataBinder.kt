package com.mlsdev.mlsdevstore.presentaion.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import android.widget.ImageView

import com.mlsdev.mlsdevstore.R
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.BottomBarTab
import com.squareup.picasso.Picasso

object DataBinder {

    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, imageUrl: String) {
        Picasso.with(imageView.context)
                .load(imageUrl)
                .error(R.drawable.bg_splash_screen)
                .placeholder(R.drawable.bg_splash_screen)
                .into(imageView)
    }

    @BindingAdapter("badgeCount")
    fun setBadgeCount(bottomBar: BottomBar, badgeCount: Int) {
        val tab = bottomBar.getTabWithId(R.id.navigation_item_cart)
        tab.setBadgeCount(badgeCount)
    }

    @BindingAdapter("error")
    fun setError(inputLayout: TextInputLayout, error: String?) {
        if (error == null || error.isEmpty()) {
            inputLayout.isErrorEnabled = false
        } else {
            inputLayout.isErrorEnabled = true
            inputLayout.error = error
        }
    }

}
