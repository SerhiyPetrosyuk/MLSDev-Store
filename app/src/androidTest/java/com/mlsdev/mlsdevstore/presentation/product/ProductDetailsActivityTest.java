package com.mlsdev.mlsdevstore.presentation.product;


import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.mlsdev.mlsdevstore.AssetUtils;
import com.mlsdev.mlsdevstore.MockMLSDevStoreApplication;
import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.presentaion.product.ProductDetailsActivity;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;
import com.mlsdev.mlsdevstore.presentaion.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Single;

public class ProductDetailsActivityTest {
    private Item item;
    private Intent intent;

    @Inject
    DataSource dataSource;

    @Inject
    Utils utils;

    @Rule
    public ActivityTestRule<ProductDetailsActivity> rule = new ActivityTestRule<>(
            ProductDetailsActivity.class, true, false);

    @Before
    public void setUp() throws IOException {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        item = AssetUtils.getProductItem();
        intent = new Intent(instrumentation.getTargetContext(), ProductDetailsActivity.class);
        intent.putExtra(ExtrasKeys.PRODUCT_DETAILS, item);
        MockMLSDevStoreApplication mockApplication = (MockMLSDevStoreApplication) instrumentation.getTargetContext().getApplicationContext();
        mockApplication.getComponent().inject(this);
    }

    @Test
    public void someTest() {
        Mockito.when(utils.isNetworkAvailable()).thenReturn(true);
        Mockito.when(dataSource.getItem(item.getId())).thenReturn(Single.just(item));
        rule.launchActivity(intent);
        Espresso.onView(ViewMatchers.withText(item.getTitle())).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
