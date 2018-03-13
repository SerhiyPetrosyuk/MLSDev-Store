package com.mlsdev.mlsdevstore.inject.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mlsdev.mlsdevstore.dependency_injection.module.ApplicationModule;
import com.mlsdev.mlsdevstore.presentaion.utils.FieldsValidator;
import com.mlsdev.mlsdevstore.presentaion.utils.Utils;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
abstract public class MockApplicationModule extends ApplicationModule {

    @Provides
    @Singleton
    static Context provideContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Provides
    @Singleton
    static Utils providesUtils() {
        return Mockito.mock(Utils.class);
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    static FieldsValidator provideFieldsValidator() {
        return Mockito.mock(FieldsValidator.class);
    }

    @Provides
    @Singleton
    static SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

}
