package com.mlsdev.mlsdevstore.presentaion.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.model.item.Image;
import com.mlsdev.mlsdevstore.databinding.LayoutGalleryItemBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ProductGalleryAdapter extends PagerAdapter {
    private List<Image> images = new ArrayList<>();

    @Inject
    public ProductGalleryAdapter() {
    }

    public void setImages(List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Image image = images.get(position);

        LayoutGalleryItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.layout_gallery_item,
                container,
                false);

        binding.setImageUrl(image.getImageUrl());

        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
