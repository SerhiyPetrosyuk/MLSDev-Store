package com.mlsdev.mlsdevstore.presentation.categories;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.presentaion.categories.CategoriesViewModel;
import com.mlsdev.mlsdevstore.presentaion.utils.Utils;
import com.mlsdev.mlsdevstore.presentation.viewmodel.BaseViewModelTest;
import com.mlsdev.mlsdevstore.utils.RxUtils;
import com.mlsdev.mlsdevstore.utils.UnitAssetsUtils;

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
import retrofit2.HttpException;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE, sdk = 26)
public class CategoriesViewModelTest extends BaseViewModelTest {

    @Mock
    DataSource dataSource;

    @Mock
    Utils utils;

    private CategoriesViewModel viewModel;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        viewModel = spy(new CategoriesViewModel(dataSource, utils));
        when(utils.isNetworkAvailable()).thenReturn(true);
    }

    @Test
    public void getAllRootCategories() {
        CategoryTree categoryTree = UnitAssetsUtils.getCategoryTree();
        when(dataSource.getRootCategoryTree()).thenReturn(Single.just(categoryTree));
        viewModel.getRootCategories();

        verify(dataSource, times(1)).getRootCategoryTree();
        Assert.assertEquals(
                categoryTree.getCategoryTreeNode().getChildCategoryTreeNodes().size(),
                viewModel.listObservableField.get().size());
    }

    @Test
    public void getAllRootCategories_NoNetworkConnection() {
        when(utils.isNetworkAvailable()).thenReturn(false);
        viewModel.getRootCategories();
        verify(dataSource, times(0)).getRootCategoryTree();
        Assert.assertTrue(viewModel.networkErrorOccurred.get());
        Assert.assertNull(viewModel.listObservableField.get());
    }

    @Test
    public void getAllRootCategories_NotAuthorizedError() {
        HttpException httpException = mock(HttpException.class);
        when(httpException.code()).thenReturn(401);
        when(dataSource.getRootCategoryTree()).thenReturn(Single.error(httpException));

        viewModel.getRootCategories();

        verify(viewModel, times(1)).onAuthorizationErrorOccurred();
        Assert.assertTrue(viewModel.authErrorOccurred.get());
    }

    @Test
    public void getAllRootCategories_ServerError() {
        HttpException httpException = mock(HttpException.class);
        when(httpException.code()).thenReturn(500);
        when(dataSource.getRootCategoryTree()).thenReturn(Single.error(httpException));

        viewModel.getRootCategories();

        verify(viewModel, times(1)).onTechnicalErrorOccurred();
        Assert.assertTrue(viewModel.technicalErrorOccurred.get());
    }

}
