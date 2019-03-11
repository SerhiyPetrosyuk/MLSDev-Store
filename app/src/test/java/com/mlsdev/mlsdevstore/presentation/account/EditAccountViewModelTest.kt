package com.mlsdev.mlsdevstore.presentation.account

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mlsdev.mlsdevstore.data.local.LocalDataSource
import com.mlsdev.mlsdevstore.data.model.user.Address
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo
import com.mlsdev.mlsdevstore.presentaion.account.EditAccountViewModel
import com.mlsdev.mlsdevstore.presentaion.utils.EMAIL
import com.mlsdev.mlsdevstore.presentaion.utils.FIRST_NAME
import com.mlsdev.mlsdevstore.presentaion.utils.FieldsValidator
import com.mlsdev.mlsdevstore.presentaion.utils.LAST_NAME
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert.assertEquals
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

    @Mock
    lateinit var observer: Observer<Boolean>

    private var locale = Locale("en", "US")
    private lateinit var viewModel: EditAccountViewModel
    private val personalInfo = PersonalInfo()
    private val personalInfoUpdate = PersonalInfo()
    private val address = Address()
    private val addressUpdate = Address()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun beforeTest() {
        personalInfo.contactFirstName = "John"
        personalInfo.contactLastName = "Smith"
        personalInfo.contactEmail = "email@email.com"

        address.phoneNumber = "+151555555"
        address.postalCode = "19101"
        address.address = "1000 Vine Street"
        address.state = "PA"
        address.city = "Philadelphia"

        personalInfoUpdate.contactFirstName = "${personalInfo.contactFirstName}_"
        personalInfoUpdate.contactLastName = "${personalInfo.contactLastName}_"
        personalInfoUpdate.contactEmail = "${personalInfo.contactEmail}_"

        addressUpdate.phoneNumber = "${address.phoneNumber}_"
        addressUpdate.postalCode = "${address.postalCode}_"
        addressUpdate.address = "${address.address}_"
        addressUpdate.state = "${address.state}_"
        addressUpdate.city = "${address.city}_"

        MockitoAnnotations.initMocks(this)
        `when`(fieldsValidator.putField(EMAIL, personalInfoUpdate.contactEmail)).thenReturn(fieldsValidator)
        `when`(fieldsValidator.putField(FIRST_NAME, personalInfoUpdate.contactFirstName)).thenReturn(fieldsValidator)
        `when`(fieldsValidator.putField(LAST_NAME, personalInfoUpdate.contactLastName)).thenReturn(fieldsValidator)
//        `when`(application.resources).thenReturn(resources)
//        `when`(resources.configuration).thenReturn(configuration)
//        `when`(configuration.locales).thenReturn(localeList)
//        `when`(localeList[0]).thenReturn(locale)

        viewModel = EditAccountViewModel(application, dataSource, fieldsValidator)
    }

    @Test
    fun start() {
        `when`(dataSource.getPersonalInfo()).thenReturn(Single.just(personalInfo))
        `when`(dataSource.getShippingInfo()).thenReturn(Single.just(address))

        viewModel.start()

        verify<LocalDataSource>(dataSource).getPersonalInfo()
        verify<LocalDataSource>(dataSource).getShippingInfo()

        assertEquals(personalInfo.contactEmail, viewModel.email.get())
        assertEquals(personalInfo.contactFirstName, viewModel.firstName.get())
        assertEquals(personalInfo.contactLastName, viewModel.lastName.get())

        assertEquals(address.phoneNumber, viewModel.phoneNumber.get())
        assertEquals(address.postalCode, viewModel.postalCode.get())
        assertEquals(address.address, viewModel.address.get())
        assertEquals(address.state, viewModel.state.get())
        assertEquals(address.city, viewModel.city.get())
    }

    @Test
    fun onSubmitPersonalInfoClick() {
        `when`(fieldsValidator.validateFields()).thenReturn(Completable.complete())
        `when`(dataSource.updatePersonalInfo(personalInfoUpdate.contactEmail, personalInfoUpdate.contactFirstName, personalInfoUpdate.contactLastName))
                .thenReturn(Single.just(personalInfoUpdate))

        viewModel.email.set(personalInfoUpdate.contactEmail)
        viewModel.firstName.set(personalInfoUpdate.contactFirstName)
        viewModel.lastName.set(personalInfoUpdate.contactLastName)

        viewModel.profileDataUpdated.observeForever(observer)
        viewModel.onSubmitPersonalInfoClick()

        verify(observer).onChanged(true)
        verify(dataSource).updatePersonalInfo(
                personalInfoUpdate.contactEmail,
                personalInfoUpdate.contactFirstName,
                personalInfoUpdate.contactLastName)
    }

}
