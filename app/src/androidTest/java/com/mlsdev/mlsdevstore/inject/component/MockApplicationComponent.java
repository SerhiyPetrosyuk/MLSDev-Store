package com.mlsdev.mlsdevstore.inject.component;


import com.mlsdev.mlsdevstore.MLSDevStoreApplication;
import com.mlsdev.mlsdevstore.dependency_injection.module.ApiModule;
import com.mlsdev.mlsdevstore.dependency_injection.module.DatabaseModule;
import com.mlsdev.mlsdevstore.dependency_injection.module.FragmentBuilderModule;
import com.mlsdev.mlsdevstore.dependency_injection.module.ProductsAdapterModule;
import com.mlsdev.mlsdevstore.dependency_injection.module.ViewModelModule;
import com.mlsdev.mlsdevstore.inject.module.MockApplicationModule;
import com.mlsdev.mlsdevstore.inject.module.MockDataSourceModule;
import com.mlsdev.mlsdevstore.presentation.product.ProductDetailsActivityTest;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        MockApplicationModule.class,
        FragmentBuilderModule.class,
        ViewModelModule.class,
        ApiModule.class,
        DatabaseModule.class,
        MockDataSourceModule.class,
        ProductsAdapterModule.class
})
public interface MockApplicationComponent extends AndroidInjector<MLSDevStoreApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MLSDevStoreApplication> {
    }

    void inject(ProductDetailsActivityTest activityTest);

}
