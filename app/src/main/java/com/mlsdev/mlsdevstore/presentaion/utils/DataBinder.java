package com.mlsdev.mlsdevstore.presentaion.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.mlsdev.mlsdevstore.R;
import com.squareup.picasso.Picasso;

public final class DataBinder {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        Picasso.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.bg_splash_screen)
                .error(R.drawable.bg_splash_screen)
                .into(imageView);
    }

}
