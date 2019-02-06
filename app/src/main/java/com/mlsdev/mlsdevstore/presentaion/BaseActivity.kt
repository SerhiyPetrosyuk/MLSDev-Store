package com.mlsdev.mlsdevstore.presentaion

import android.os.Bundle
import android.view.MenuItem

import javax.inject.Inject

import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var errorInViewHandler: ErrorInViewHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        errorInViewHandler.setContext(this)
    }

    fun displayBackArrow(display: Boolean) {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(display)
        }
    }

    fun initToolbar(toolbar: Toolbar?) {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed()
    }

    open fun setToolBarTitle(@StringRes title: Int) {
        if (supportActionBar != null)
            supportActionBar!!.setTitle(title)
    }
}
