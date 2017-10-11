package com.mlsdev.mlsdevstore.dependency_injection.module;

import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface ActivityModule {

    @ContributesAndroidInjector(modules = {FragmentBuilderModule.class})
    MainActivity contributeMainActivity();

}
