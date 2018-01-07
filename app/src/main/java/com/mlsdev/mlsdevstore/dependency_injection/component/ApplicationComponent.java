package com.mlsdev.mlsdevstore.dependency_injection.component;


import com.mlsdev.mlsdevstore.MLSDevStoreApplication;
import com.mlsdev.mlsdevstore.dependency_injection.module.ApiModule;
import com.mlsdev.mlsdevstore.dependency_injection.module.ApplicationModule;
import com.mlsdev.mlsdevstore.dependency_injection.module.DataSourceModule;
import com.mlsdev.mlsdevstore.dependency_injection.module.DatabaseModule;
import com.mlsdev.mlsdevstore.dependency_injection.module.FragmentBuilderModule;
import com.mlsdev.mlsdevstore.dependency_injection.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        FragmentBuilderModule.class,
        ViewModelModule.class,
        ApiModule.class,
        DatabaseModule.class,
        DataSourceModule.class
})
public interface ApplicationComponent extends AndroidInjector<MLSDevStoreApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MLSDevStoreApplication> {
    }

}
