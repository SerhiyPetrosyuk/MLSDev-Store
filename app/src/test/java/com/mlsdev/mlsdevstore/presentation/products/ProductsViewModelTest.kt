package com.mlsdev.mlsdevstore.presentation.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mlsdev.mlsdevstore.data.DataLoadState
import com.mlsdev.mlsdevstore.data.repository.ProductsRepository
import com.mlsdev.mlsdevstore.data.repository.SearchImageRepository
import com.mlsdev.mlsdevstore.presentaion.products.ProductsViewModel
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductsViewModelTest {

    @Mock
    lateinit var utils: Utils

    @Mock
    lateinit var productsRepository: ProductsRepository

    @Mock
    lateinit var imagesRepository: SearchImageRepository

    @Mock
    lateinit var loadingStateObserver: Observer<DataLoadState>

    lateinit var viewModel: ProductsViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        viewModel = ProductsViewModel(utils, productsRepository, imagesRepository)
    }

    @Test
    fun observeLoadingState() {
        Mockito.`when`(productsRepository.getPageLoadingState()).thenReturn(MutableLiveData<DataLoadState>(DataLoadState.LOADING))
        viewModel.loadingState.observeForever(loadingStateObserver)

        Mockito.verify(loadingStateObserver).onChanged(DataLoadState.LOADING)
        Assert.assertEquals(DataLoadState.LOADING, viewModel.loadingState.value)
    }

}