package com.mlsdev.mlsdevstore.presentaion.utils;

import androidx.databinding.BindingAdapter;
import com.google.android.material.textfield.TextInputLayout;
import android.widget.ImageView;

import com.mlsdev.mlsdevstore.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.squareup.picasso.Picasso;

public final class DataBinder {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        Picasso.with(imageView.getContext())
                .load(imageUrl)
                .error(R.drawable.bg_splash_screen)
                .placeholder(R.drawable.bg_splash_screen)
                .into(imageView);
    }

    @BindingAdapter("badgeCount")
    public static void setBadgeCount(BottomBar bottomBar, int badgeCount) {
        BottomBarTab tab = bottomBar.getTabWithId(R.id.navigation_item_cart);
        tab.setBadgeCount(badgeCount);
    }

    @BindingAdapter("error")
    public static void setError(TextInputLayout inputLayout, String error) {
        if (error == null || error.isEmpty()){
            inputLayout.setErrorEnabled(false);
        } else {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(error);
        }
    }

}
