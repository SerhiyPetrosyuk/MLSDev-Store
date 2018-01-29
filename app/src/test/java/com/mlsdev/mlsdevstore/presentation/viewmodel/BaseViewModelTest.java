package com.mlsdev.mlsdevstore.presentation.viewmodel;

import com.mlsdev.mlsdevstore.utils.RxUtils;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import io.reactivex.plugins.RxJavaPlugins;

public class BaseViewModelTest {

    @BeforeClass
    public static void beforeClass() {
        RxUtils.setUpRxSchedulers();
    }

    @AfterClass
    public static void afterClass() {
        RxJavaPlugins.reset();
    }

}
