package com.mlsdev.mlsdevstore.presentation.product;

import android.os.Bundle;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.model.item.Item;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Single;

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

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        item = UnitAssetsUtils.getProductItem();
        itemData = new Bundle();
        itemData.putParcelable(ExtrasKeys.PRODUCT_DETAILS, item);
        viewModel = Mockito.spy(new ProductDetailsViewModel(dataSource, utils));
    }

    @Test
    public void setProductDetailsData() {
        Mockito.when(dataSource.getItem(item.getId())).thenReturn(Single.just(item));
        Mockito.when(utils.isNetworkAvailable()).thenReturn(true);
        viewModel.setProductDetailsData(itemData);
        Assert.assertEquals(item.getTitle(), viewModel.title.get());
        Assert.assertEquals(item.getImage(), viewModel.imageUrl.get());
        Assert.assertEquals(String.valueOf(item.getPrice().getValue()), viewModel.price.get());
        Assert.assertEquals(item.getPrice().getCurrency(), viewModel.currency.get());
        Mockito.verify(dataSource, Mockito.times(1)).getItem(item.getId());
    }

    @Test
    public void setProductDetailsData_NoNetworkConnection() {
        Mockito.when(dataSource.getItem(item.getId())).thenReturn(Single.just(item));
        Mockito.when(utils.isNetworkAvailable()).thenReturn(false);
        viewModel.setProductDetailsData(itemData);
        Mockito.verify(dataSource, Mockito.never()).getItem(item.getId());
    }

    @Test
    public void setProductDetailsData_DataIsNull() {
        Mockito.when(dataSource.getItem(Mockito.anyString())).thenReturn(Single.just(item));
        viewModel.setProductDetailsData(null);
        Assert.assertNull(viewModel.title.get());
        Assert.assertNull(viewModel.imageUrl.get());
        Assert.assertNull(viewModel.price.get());
        Assert.assertNull(viewModel.currency.get());
        Mockito.verify(dataSource, Mockito.never()).getItem(Mockito.anyString());
    }

}
