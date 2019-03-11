package com.mlsdev.mlsdevstore.presentation.cart;

import androidx.test.filters.LargeTest;

@LargeTest
public class CartFragmentTest {
//    private Product item;
//    private Intent intent;
//    private String price;
//
//    @Inject
//    Cart cart;
//
//    @Rule
//    public IntentsTestRule<AppActivity> rule = new IntentsTestRule<>(
//            AppActivity.class, true, false);
//
//
//    @Before
//    public void beforeTest() {
//        MockitoAnnotations.initMocks(this);
//        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
//
//        MockMLSDevStoreApplication mockApplication = (MockMLSDevStoreApplication) instrumentation.getTargetContext().getApplicationContext();
//        mockApplication.getComponent().inject(this);
//
//
//        item = AssetUtils.getProductItem();
//        intent = new Intent(instrumentation.getTargetContext(), AppActivity.class);
//        intent.putExtra(ExtrasKeys.KEY_PRODUCT_DETAILS, item);
//        price = "$196.00";
//    }
//
//    private void launchActivity() {
//        rule.launchActivity(intent);
//        rule.getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.content, new CartFragment())
//                .commit();
//    }
//
//    @Test
//    public void cartIsEmpty() {
//        cart.reset();
//
//        launchActivity();
//
//        onView(withText(R.string.label_empty_cart)).check(matches(isDisplayed()));
//        onView(withId(R.id.button_checkout)).check(matches(not(isDisplayed())));
//    }
//
//    @Test
//    public void cartWithItems() {
//        cart.addItem(item);
//
//        launchActivity();
//
//        onView(withId(R.id.button_checkout)).check(matches(isDisplayed()));
//        onView(withText(R.string.label_empty_cart)).check(matches(not(isDisplayed())));
//        onView(withText(R.string.label_total_sum)).check(matches(isDisplayed()));
//        onView(withText(price)).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void removeItem() {
//        cart.addItem(item);
//
//        launchActivity();
//
//        onView(withId(R.id.button_remove_from_cart)).perform(click());
//        onView(withText(R.string.label_empty_cart)).check(matches(isDisplayed()));
//        onView(withId(R.id.button_checkout)).check(matches(not(isDisplayed())));
//    }
//
//    @Test
//    public void goToCheckoutScreen() {
//        cart.addItem(item);
//
//        launchActivity();
//
//        onView(withId(R.id.button_checkout)).perform(click());
//        Intents.intended(IntentMatchers.hasComponent(CheckoutActivity.class.getName()));
//    }

}
