package com.mlsdev.mlsdevstore.presentation.store;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.presentaion.store.StoreViewModel;
import com.mlsdev.mlsdevstore.presentaion.utils.Utils;
import com.mlsdev.mlsdevstore.presentation.viewmodel.BaseViewModelTest;
import com.mlsdev.mlsdevstore.utils.RxUtils;
import com.mlsdev.mlsdevstore.utils.UnitAssetsUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE, sdk = 26)
public class StoreViewModelTest extends BaseViewModelTest {

    @Mock
    DataSource dataSource;

    @Mock
    Utils utils;

    private StoreViewModel viewModel;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        viewModel = spy(new StoreViewModel(dataSource, utils));
        when(utils.isNetworkAvailable()).thenReturn(true);
    }

    @Test
    public void getProducts() {
        SearchResult searchResult = UnitAssetsUtils.getSearchItemsResult();
        when(dataSource.searchItemsByRandomCategory()).thenReturn(Single.just(searchResult));

        viewModel.getProducts();

        verify(dataSource, times(1)).searchItemsByRandomCategory();
        Assert.assertEquals(viewModel.searchResult.get(), searchResult);
    }

    @Test
    public void getProducts_NoNetworkConnection() {
        when(utils.isNetworkAvailable()).thenReturn(false);
        viewModel.getProducts();
        Assert.assertTrue(viewModel.networkErrorOccurred.get());
        verify(dataSource, times(0)).searchItemsByRandomCategory();
    }

    @Test
    public void refresh() {
        SearchResult searchResult = UnitAssetsUtils.getSearchItemsResult();
        when(dataSource.searchItemsByRandomCategory()).thenReturn(Single.just(searchResult));

        viewModel.refresh();

        verify(dataSource, times(1)).resetSearchResults();
        verify(dataSource, times(1)).searchItemsByRandomCategory();
        Assert.assertEquals(viewModel.searchResult.get(), searchResult);
    }

    @Test
    public void refresh_NoNetworkConnection() {
        when(utils.isNetworkAvailable()).thenReturn(false);
        viewModel.refresh();
        Assert.assertTrue(viewModel.networkErrorOccurred.get());
        verify(dataSource, times(0)).resetSearchResults();
        verify(dataSource, times(0)).searchItemsByRandomCategory();
    }

    @Test
    public void loadMoreItemsFromRandomCategory() {
        SearchResult searchResultFirstPage = UnitAssetsUtils.getSearchItemsResult();
        SearchResult searchResultSecondPage = UnitAssetsUtils.getSearchMoreItemsResult();
        when(dataSource.searchMoreItemsByRandomCategory()).thenReturn(Single.just(searchResultSecondPage));
        viewModel.searchResult.set(searchResultFirstPage);

        viewModel.loadMoreItemsFromRandomCategory();

        verify(dataSource, times(1)).searchMoreItemsByRandomCategory();
        Assert.assertEquals(viewModel.searchResult.get().getItemSummaries().size(), 20);
        Assert.assertEquals(
                viewModel.searchResult.get().getItemSummaries().get(10).getId(),
                searchResultSecondPage.getItemSummaries().get(0).getId());

    }

    @Test
    public void loadMoreItemsFromRandomCategory_NoNetworkConnection() {
        when(utils.isNetworkAvailable()).thenReturn(false);
        viewModel.loadMoreItemsFromRandomCategory();
        Assert.assertTrue(viewModel.networkErrorOccurred.get());
        verify(dataSource, times(0)).searchMoreItemsByRandomCategory();
    }

}
