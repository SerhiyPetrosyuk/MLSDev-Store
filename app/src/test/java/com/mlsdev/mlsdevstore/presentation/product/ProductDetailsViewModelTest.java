package com.mlsdev.mlsdevstore.presentation.product;

import android.content.Context;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.data.model.product.Item;
import com.mlsdev.mlsdevstore.presentaion.product.ProductDetailsViewModel;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;
import com.mlsdev.mlsdevstore.presentaion.utils.Utils;
import com.mlsdev.mlsdevstore.presentation.viewmodel.BaseViewModelTest;
import com.mlsdev.mlsdevstore.utils.UnitAssetsUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE, sdk = 26)
public class ProductDetailsViewModelTest extends BaseViewModelTest {
    private Item item;
    private Bundle itemData;
    private ProductDetailsViewModel viewModel;

    @Mock
    DataSource dataSource;

    @Mock
    Utils utils;

    @Mock
    Cart cart;

    @Mock
    Context context;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        item = UnitAssetsUtils.getProductItem();
        itemData = new Bundle();
        itemData.putParcelable(ExtrasKeys.KEY_PRODUCT_DETAILS, item);
        viewModel = spy(new ProductDetailsViewModel(context, dataSource, utils, cart));
    }

    @Test
    public void setProductDetailsData() {
        when(dataSource.getItem(item.getId())).thenReturn(Single.just(item));
        when(utils.isNetworkAvailable()).thenReturn(true);
        viewModel.setProductDetailsData(itemData);
        Assert.assertEquals(item.getItemTitle(), viewModel.getTitle().get());
        Assert.assertEquals(item.getImageUrl(), viewModel.getImageUrl().get());
        Assert.assertEquals(String.valueOf(item.getItemPrice().getValue()), viewModel.getPrice().get());
        Assert.assertEquals(item.getItemPrice().getCurrency(), viewModel.getCurrency().get());
        verify(dataSource, times(1)).getItem(item.getId());
    }

    @Test
    public void setProductDetailsData_NoNetworkConnection() {
        when(dataSource.getItem(item.getId())).thenReturn(Single.just(item));
        when(utils.isNetworkAvailable()).thenReturn(false);
        viewModel.setProductDetailsData(itemData);
        verify(dataSource, never()).getItem(item.getId());
    }

    @Test
    public void setProductDetailsData_DataIsNull() {
        when(dataSource.getItem(anyString())).thenReturn(Single.just(item));
        viewModel.setProductDetailsData(null);
        Assert.assertNull(viewModel.getTitle().get());
        Assert.assertNull(viewModel.getImageUrl().get());
        Assert.assertNull(viewModel.getPrice().get());
        Assert.assertNull(viewModel.getCurrency().get());
        verify(dataSource, never()).getItem(anyString());
    }

    @Test
    public void getProductDetails_ProductWasNotFound() {
        String errorBody = UnitAssetsUtils.getJsonStringFromResources("item_not_found.json");
        Response response = Response.error(404, ResponseBody.create(null, errorBody));
        HttpException exception = mock(HttpException.class);
        when(exception.response()).thenReturn(response);
        when(dataSource.getItem(item.getId())).thenReturn(Single.error(exception));
        when(utils.isNetworkAvailable()).thenReturn(true);
        viewModel.setProductDetailsData(itemData);
        verify(dataSource, times(1)).getItem(item.getId());
        verify(viewModel, times(1)).onCommonErrorOccurred();
    }

}
