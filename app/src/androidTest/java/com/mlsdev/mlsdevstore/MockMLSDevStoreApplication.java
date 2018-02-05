package com.mlsdev.mlsdevstore;

import com.mlsdev.mlsdevstore.inject.component.DaggerMockApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class MockMLSDevStoreApplication extends MLSDevStoreApplication {
    DaggerMockApplicationComponent component;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        component = (DaggerMockApplicationComponent) DaggerMockApplicationComponent.builder().create(this);
        return DaggerMockApplicationComponent.builder().create(this);
    }

    public DaggerMockApplicationComponent getComponent() {
        return component;
    }
}
