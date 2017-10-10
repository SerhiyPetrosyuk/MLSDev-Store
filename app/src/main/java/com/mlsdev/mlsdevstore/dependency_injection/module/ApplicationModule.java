package com.mlsdev.mlsdevstore.dependency_injection.module;

import android.content.Context;

import com.mlsdev.mlsdevstore.MLSDevStoreApplication;
import com.mlsdev.mlsdevstore.dependency_injection.component.scope.ActivityScope;
import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class ApplicationModule {

    @Provides
    @Singleton
    Context provideContext(MLSDevStoreApplication application) {
        return application.getApplicationContext();
    }

    @ContributesAndroidInjector(modules = {ActivityModule.class})
    @ActivityScope
    abstract MainActivity mainActivity();

}
