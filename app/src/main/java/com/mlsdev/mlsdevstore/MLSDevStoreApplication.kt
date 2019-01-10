package com.mlsdev.mlsdevstore

import com.mlsdev.mlsdevstore.injections.component.DaggerApplicationComponent

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

open class MLSDevStoreApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().create(this)
    }

}
