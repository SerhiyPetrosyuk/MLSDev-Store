package com.mlsdev.mlsdevstore.presentaion.bottom_navigation;

import android.os.Bundle;

import com.mlsdev.mlsdevstore.R;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
