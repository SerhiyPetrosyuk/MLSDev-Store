package com.mlsdev.mlsdevstore.dependency_injection.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mlsdev.mlsdevstore.MLSDevStoreApplication;
import com.mlsdev.mlsdevstore.dependency_injection.component.scope.ActivityScope;
import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity;
import com.mlsdev.mlsdevstore.presentaion.splashscreen.SplashScreenActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class ApplicationModule {

    @Provides
    @Singleton
    static Context provideContext(MLSDevStoreApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    static SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @ContributesAndroidInjector(modules = {ActivityModule.class})
    @ActivityScope
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = {ActivityModule.class})
    @ActivityScope
    abstract SplashScreenActivity splashScreenActivity();

}
