package com.mlsdev.mlsdevstore.presentation.cart;

import android.app.Instrumentation;
import android.content.Intent;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;

import com.mlsdev.mlsdevstore.AssetUtils;
import com.mlsdev.mlsdevstore.MockMLSDevStoreApplication;
import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity;
import com.mlsdev.mlsdevstore.presentaion.cart.CartFragment;
import com.mlsdev.mlsdevstore.presentaion.checkout.CheckoutActivity;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@LargeTest
public class CartFragmentTest {
    private Item item;
    private Intent intent;
    private String price;

    @Inject
    Cart cart;

    @Rule
    public IntentsTestRule<MainActivity> rule = new IntentsTestRule<>(
            MainActivity.class, true, false);


    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        MockMLSDevStoreApplication mockApplication = (MockMLSDevStoreApplication) instrumentation.getTargetContext().getApplicationContext();
        mockApplication.getComponent().inject(this);


        item = AssetUtils.getProductItem();
        intent = new Intent(instrumentation.getTargetContext(), MainActivity.class);
        intent.putExtra(ExtrasKeys.KEY_PRODUCT_DETAILS, item);
        price = "$196.00";
    }

    private void launchActivity() {
        rule.launchActivity(intent);
        rule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new CartFragment())
                .commit();
    }

    @Test
    public void cartIsEmpty() {
        cart.reset();

        launchActivity();

        onView(withText(R.string.label_empty_cart)).check(matches(isDisplayed()));
        onView(withId(R.id.button_checkout)).check(matches(not(isDisplayed())));
    }

    @Test
    public void cartWithItems() {
        cart.addItem(item);

        launchActivity();

        onView(withId(R.id.button_checkout)).check(matches(isDisplayed()));
        onView(withText(R.string.label_empty_cart)).check(matches(not(isDisplayed())));
        onView(withText(R.string.label_total_sum)).check(matches(isDisplayed()));
        onView(withText(price)).check(matches(isDisplayed()));
    }

    @Test
    public void removeItem() {
        cart.addItem(item);

        launchActivity();

        onView(withId(R.id.button_remove_from_cart)).perform(click());
        onView(withText(R.string.label_empty_cart)).check(matches(isDisplayed()));
        onView(withId(R.id.button_checkout)).check(matches(not(isDisplayed())));
    }

    @Test
    public void goToCheckoutScreen() {
        cart.addItem(item);

        launchActivity();

        onView(withId(R.id.button_checkout)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(CheckoutActivity.class.getName()));
    }

}
