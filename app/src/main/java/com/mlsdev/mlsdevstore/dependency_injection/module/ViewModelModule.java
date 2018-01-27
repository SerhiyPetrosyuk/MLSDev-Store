package com.mlsdev.mlsdevstore.dependency_injection.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.mlsdev.mlsdevstore.dependency_injection.ViewModelFactory;
import com.mlsdev.mlsdevstore.presentaion.account.AccountViewModel;
import com.mlsdev.mlsdevstore.presentaion.cart.CartViewModel;
import com.mlsdev.mlsdevstore.presentaion.categories.CategoriesViewModel;
import com.mlsdev.mlsdevstore.presentaion.splashscreen.SplashScreenViewModel;
import com.mlsdev.mlsdevstore.presentaion.store.StoreViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StoreViewModel.class)
    ViewModel bindStoreViewModel(StoreViewModel storeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel.class)
    ViewModel bindCartViewModel(CartViewModel cartViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel.class)
    ViewModel bindAccountViewModel(AccountViewModel accountViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel.class)
    ViewModel bindSplashScreenViewModel(SplashScreenViewModel splashScreenViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel.class)
    ViewModel bindCategoriesViewModel(CategoriesViewModel categoriesViewModel);

    @Binds
    ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
