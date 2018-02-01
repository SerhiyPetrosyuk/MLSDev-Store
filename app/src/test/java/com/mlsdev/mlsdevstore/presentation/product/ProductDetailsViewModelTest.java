package com.mlsdev.mlsdevstore.presentation.product;

import android.os.Bundle;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.presentaion.product.ProductDetailsViewModel;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;
import com.mlsdev.mlsdevstore.presentation.viewmodel.BaseViewModelTest;
import com.mlsdev.mlsdevstore.utils.UnitAssetsUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE, sdk = 26)
public class ProductDetailsViewModelTest extends BaseViewModelTest {
    private Item item;
    private Bundle itemData;
    private ProductDetailsViewModel viewModel;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        item = UnitAssetsUtils.getProductItem();
        itemData = new Bundle();
        itemData.putParcelable(ExtrasKeys.PRODUCT_DETAILS, item);
        viewModel = Mockito.spy(new ProductDetailsViewModel());
    }

    @Test
    public void setProductDetailsData() {
        viewModel.setProductDetailsData(itemData);
        Assert.assertEquals(item.getTitle(), viewModel.title.get());
        Assert.assertEquals(item.getImage(), viewModel.imageUrl.get());
        Assert.assertEquals(String.valueOf(item.getPrice().getValue()), viewModel.price.get());
        Assert.assertEquals(item.getPrice().getCurrency(), viewModel.currency.get());
    }

    @Test
    public void setProductDetailsData_DataIsNull() {
        viewModel.setProductDetailsData(null);
        Assert.assertNull(viewModel.title.get());
        Assert.assertNull(viewModel.imageUrl.get());
    }

}
