package com.mlsdev.mlsdevstore.inject.component;


import com.mlsdev.mlsdevstore.MLSDevStoreApplication;
import com.mlsdev.mlsdevstore.injections.module.AdapterModule;
import com.mlsdev.mlsdevstore.injections.module.ApiModule;
import com.mlsdev.mlsdevstore.injections.module.DatabaseModule;
import com.mlsdev.mlsdevstore.injections.module.FragmentBuilderModule;
import com.mlsdev.mlsdevstore.injections.module.ProductsAdapterModule;
import com.mlsdev.mlsdevstore.injections.module.ViewModelModule;
import com.mlsdev.mlsdevstore.inject.module.MockApplicationModule;
import com.mlsdev.mlsdevstore.inject.module.MockDataSourceModule;
import com.mlsdev.mlsdevstore.presentation.account.EditPersonalInfoActivityTest;
import com.mlsdev.mlsdevstore.presentation.cart.CartFragmentTest;
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
        ProductsAdapterModule.class,
        AdapterModule.class
})
public interface MockApplicationComponent extends AndroidInjector<MLSDevStoreApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MLSDevStoreApplication> {
    }

    void inject(ProductDetailsActivityTest activityTest);

    void inject(CartFragmentTest cartFragmentTest);

    void inject(EditPersonalInfoActivityTest editPersonalInfoActivityTest);
}
