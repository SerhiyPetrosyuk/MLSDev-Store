package com.mlsdev.mlsdevstore.presentaion.bottom_navigation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.ActivityMainBinding;
import com.mlsdev.mlsdevstore.presentaion.store.StoreFragment;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initBottomNavigation();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new StoreFragment())
                .commit();
    }

    private void initBottomNavigation() {

    }
}
