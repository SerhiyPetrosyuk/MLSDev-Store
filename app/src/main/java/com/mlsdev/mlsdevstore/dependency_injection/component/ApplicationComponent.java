package com.mlsdev.mlsdevstore.dependency_injection.component;


import com.mlsdev.mlsdevstore.MLSDevStoreApplication;
import com.mlsdev.mlsdevstore.dependency_injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        AndroidSupportInjectionModule.class
})
public interface ApplicationComponent extends AndroidInjector<MLSDevStoreApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MLSDevStoreApplication> {
    }

}
