package com.mlsdev.mlsdevstore.presentaion;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Inject
    protected ErrorInViewHandler errorInViewHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        errorInViewHandler.setContext(this);
    }

    public void displayBackArrow(boolean display) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(display);
        }
    }

    public void initToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void setToolBarTitle(@StringRes int title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }
}
