package com.mlsdev.mlsdevstore.presentation.account

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mlsdev.mlsdevstore.data.local.LocalDataSource
import com.mlsdev.mlsdevstore.data.model.user.Address
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo
import com.mlsdev.mlsdevstore.presentaion.account.EditAccountViewModel
import com.mlsdev.mlsdevstore.presentaion.utils.FieldsValidator
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class EditAccountViewModelTest {

    @Mock
    lateinit var dataSource: LocalDataSource

    @Mock
    lateinit var fieldsValidator: FieldsValidator

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var resources: Resources

    @Mock
    lateinit var configuration: Configuration

    @Mock
    lateinit var localeList: LocaleList

    private var locale = Locale("en", "US")
    private lateinit var viewModel: EditAccountViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
//        `when`(application.resources).thenReturn(resources)
//        `when`(resources.configuration).thenReturn(configuration)
//        `when`(configuration.locales).thenReturn(localeList)
//        `when`(localeList[0]).thenReturn(locale)
        viewModel = EditAccountViewModel(application, dataSource, fieldsValidator)
    }

    @Test
    fun start() {
        `when`(dataSource.getPersonalInfo()).thenReturn(Single.just(PersonalInfo()))
        `when`(dataSource.getShippingInfo()).thenReturn(Single.just(Address()))

        viewModel.start()

        verify<LocalDataSource>(dataSource).getPersonalInfo()
        verify<LocalDataSource>(dataSource).getShippingInfo()
    }

}
