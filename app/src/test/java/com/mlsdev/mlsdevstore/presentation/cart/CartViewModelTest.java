package com.mlsdev.mlsdevstore.presentation.cart;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.presentaion.cart.CartViewModel;
import com.mlsdev.mlsdevstore.presentation.viewmodel.BaseViewModelTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE, sdk = 26)
public class CartViewModelTest extends BaseViewModelTest {

    @Spy
    Cart cart;
    private CartViewModel viewModel;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        viewModel = spy(new CartViewModel(cart));
    }

    @Test
    public void cartIsEmpty() {
        viewModel.onStart();
        assertTrue(viewModel.getCartIsEmpty().get());
    }

    @Test
    public void cartIsNotEmpty() {
        viewModel.onStart();
        cart.addItem(new Item());
        assertFalse(viewModel.getCartIsEmpty().get());
    }

}
