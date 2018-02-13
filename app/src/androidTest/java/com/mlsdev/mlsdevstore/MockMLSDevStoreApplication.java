package com.mlsdev.mlsdevstore;

import com.mlsdev.mlsdevstore.inject.component.DaggerMockApplicationComponent;
import com.mlsdev.mlsdevstore.inject.component.MockApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class MockMLSDevStoreApplication extends MLSDevStoreApplication {
    private AndroidInjector<? extends DaggerApplication> injector;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        injector = DaggerMockApplicationComponent.builder().create(this);
        return injector;
    }

    public MockApplicationComponent getComponent() {
        return (MockApplicationComponent) injector;
    }
}
