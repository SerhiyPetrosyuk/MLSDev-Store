package com.mlsdev.mlsdevstore

import androidx.work.*
import com.mlsdev.mlsdevstore.backgroudworks.RemindWorker
import com.mlsdev.mlsdevstore.injections.component.DaggerApplicationComponent

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import java.util.concurrent.TimeUnit

open class MLSDevStoreApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        initBackgroundWorkers()
    }

    private fun initBackgroundWorkers() {
        WorkManager.initialize(this, Configuration.Builder().build())

        val constrains = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val reminderWork = PeriodicWorkRequest.Builder(RemindWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constrains)
                .build()

        WorkManager.getInstance().enqueue(reminderWork)
    }

}
