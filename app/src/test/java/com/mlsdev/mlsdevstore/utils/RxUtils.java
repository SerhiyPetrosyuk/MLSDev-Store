package com.mlsdev.mlsdevstore.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class RxUtils {

    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {

            @Override
            public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler((handler) -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler((handler) -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler((handler) -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler((handler) -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler((handler) -> immediate);
    }

}
