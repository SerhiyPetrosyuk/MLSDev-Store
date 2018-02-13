package com.mlsdev.mlsdevstore.presentation.product;


import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.mlsdev.mlsdevstore.AssetUtils;
import com.mlsdev.mlsdevstore.MockMLSDevStoreApplication;
import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.presentaion.product.ProductDetailsActivity;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;
import com.mlsdev.mlsdevstore.presentaion.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.HttpException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@LargeTest
public class ProductDetailsActivityTest {
    private Item item;
    private Intent intent;

    @Inject
    DataSource dataSource;

    @Inject
    Utils utils;

    @Rule
    public ActivityTestRule<ProductDetailsActivity> rule = new ActivityTestRule<>(
            ProductDetailsActivity.class, false, false);

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        MockMLSDevStoreApplication mockApplication = (MockMLSDevStoreApplication) instrumentation.getTargetContext().getApplicationContext();
        mockApplication.getComponent().inject(this);

        item = AssetUtils.getProductItem();
        intent = new Intent(instrumentation.getTargetContext(), ProductDetailsActivity.class);
        intent.putExtra(ExtrasKeys.PRODUCT_DETAILS, item);

        when(utils.isNetworkAvailable()).thenReturn(true);
    }

    @Test
    public void setProductDetailsData() {
        when(dataSource.getItem(item.getId())).thenReturn(Single.just(item));

        rule.launchActivity(intent);

        verify(utils, atLeastOnce()).isNetworkAvailable();
        verify(dataSource, atLeastOnce()).getItem(item.getId());

        onView(withText(item.getTitle())).check(matches(isDisplayed()));
        onView(withId(R.id.app_bar)).perform(swipeUp());
        onView(withText(item.getCondition())).check(matches(isDisplayed()));
        onView(withText(item.getBrand())).check(matches(isDisplayed()));
        onView(withText(item.getGender())).check(matches(isDisplayed()));
        onView(withText(item.getMaterial())).check(matches(isDisplayed()));
        onView(withText(item.getSize())).check(matches(isDisplayed()));
        onView(withText(item.getColor())).check(matches(isDisplayed()));
        onView(withText(String.valueOf(item.getSeller().getFeedbackScore()))).check(matches(isDisplayed()));
        onView(withText(item.getSeller().getFeedbackPercentage() + "%")).check(matches(isDisplayed()));

        onView(withId(R.id.btn_description)).perform(click());
    }

    @Test
    public void retrieveDetailedInfo_NoNetworkConnection() {
        when(utils.isNetworkAvailable()).thenReturn(false);

        rule.launchActivity(intent);

        verify(utils, atLeastOnce()).isNetworkAvailable();
        onView(withText(R.string.error_message_network)).check(matches(isDisplayed()));
    }

    @Test
    public void retrieveDetailedInfo_TechnicalError() {
        HttpException exception = mock(HttpException.class);
        when(exception.code()).thenReturn(500);
        when(dataSource.getItem(item.getId())).thenReturn(Single.error(exception));

        rule.launchActivity(intent);

        verify(utils, atLeastOnce()).isNetworkAvailable();
        verify(dataSource, atLeastOnce()).getItem(item.getId());

        onView(withText(R.string.error_message_tech)).check(matches(isDisplayed()));
    }

    @Test
    public void retrieveDetailedInfo_Timeout() {
        when(dataSource.getItem(item.getId())).thenReturn(Single.error(new SocketTimeoutException()));

        rule.launchActivity(intent);

        verify(utils, atLeastOnce()).isNetworkAvailable();
        verify(dataSource, atLeastOnce()).getItem(item.getId());

        onView(withText(R.string.error_message_network)).check(matches(isDisplayed()));
    }

}
