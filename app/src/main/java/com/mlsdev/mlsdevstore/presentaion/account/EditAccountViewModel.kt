package com.mlsdev.mlsdevstore.presentaion.account

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.mlsdev.mlsdevstore.data.local.LocalDataSource
import com.mlsdev.mlsdevstore.presentaion.utils.*
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditAccountViewModel @Inject
constructor(
        context: Context,
        private val localDataSource: LocalDataSource,
        private val fieldsValidator: FieldsValidator) : BaseViewModel() {

    init {
        this.context = context
    }

    val email = ObservableField<String>()
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()

    val phoneNumber = ObservableField<String>()
    val address = ObservableField<String>()
    val city = ObservableField<String>()
    val state = ObservableField<String>()
    val postalCode = ObservableField<String>()

    // Errors
    val emailError = ObservableField<String>()
    val firstNameError = ObservableField<String>()
    val lastNameError = ObservableField<String>()
    val phoneNumberError = ObservableField<String>()
    val addressError = ObservableField<String>()
    val cityError = ObservableField<String>()
    val stateError = ObservableField<String>()
    val postalCodeError = ObservableField<String>()

    val profileDataUpdated = MutableLiveData<Boolean>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        compositeDisposable.add(localDataSource.personalInfo.subscribe(
                { info ->
                    setIsLoading(false)
                    setIsRefreshing(false)
                    email.set(if (info.contactEmail != null) info.contactEmail else "")
                    firstName.set(if (info.contactFirstName != null) info.contactFirstName else "")
                    lastName.set(if (info.contactLastName != null) info.contactLastName else "")
                },
                { handleError(it) }))

        compositeDisposable.add(localDataSource.getShippingInfo().subscribe(
                { currentAddress ->
                    phoneNumber.set(currentAddress.phoneNumber)
                    address.set(currentAddress.address)
                    city.set(currentAddress.city)
                    state.set(currentAddress.state)
                    postalCode.set(currentAddress.postalCode)
                },
                { handleError(it) }))
    }

    fun onSubmitPersonalInfoClick() {
        compositeDisposable.add(fieldsValidator
                .putField(EMAIL, email.get())
                .putField(FIRST_NAME, firstName.get())
                .putField(LAST_NAME, lastName.get())
                .validateFields()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { this.updatePersonalInfo() },
                        { throwable ->
                            emailError.set((throwable as FieldsValidator.ValidationError).getErrorForField(EMAIL))
                            firstNameError.set(throwable.getErrorForField(FIRST_NAME))
                            lastNameError.set(throwable.getErrorForField(LAST_NAME))
                        }))
    }

    fun onSubmitShippingInfoClick() {
        compositeDisposable.add(fieldsValidator
                .putField(FIELD_PHONE, phoneNumber.get())
                .putField(FIELD_ADDRESS, address.get())
                .putField(FIELD_CITY, city.get())
                .putField(FIELD_STATE, state.get())
                .putField(FIELD_POSTAL_CODE, postalCode.get())
                .validateFields()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { this.updateShippingInfo() },
                        { throwable ->
                            phoneNumberError.set((throwable as FieldsValidator.ValidationError).getErrorForField(FIELD_PHONE))
                            addressError.set(throwable.getErrorForField(FIELD_ADDRESS))
                            cityError.set(throwable.getErrorForField(FIELD_CITY))
                            stateError.set(throwable.getErrorForField(FIELD_STATE))
                            postalCodeError.set(throwable.getErrorForField(FIELD_POSTAL_CODE))
                        }))
    }

    private fun updateShippingInfo() {
        val country: String = context!!.resources.configuration.locales[0].country
        compositeDisposable.add(
                localDataSource.updateShippingInfo(
                        phoneNumber.get() ?: "",
                        address.get() ?: "",
                        city.get() ?: "",
                        state.get() ?: "",
                        country,
                        postalCode.get() ?: "").subscribe(
                        {
                            Log.d(BaseViewModel.LOG_TAG, "Shipping info has been updated")
                            profileDataUpdated.postValue(true)
                        },
                        { handleError(it) }))
    }

    private fun updatePersonalInfo() {
        localDataSource.updatePersonalInfo(email.get(), firstName.get(), lastName.get())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        Log.d(BaseViewModel.LOG_TAG, "Personal info was updated")
                        profileDataUpdated.postValue(true)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(BaseViewModel.LOG_TAG, e.message)
                    }
                })
    }

    fun onEmailTextChanged() {
        emailError.set(null)
    }

    fun onFirstNameTextChanged() {
        firstNameError.set(null)
    }

    fun onLastNameTextChanged() {
        lastNameError.set(null)
    }

    fun onPhoneChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        phoneNumberError.set(null)
    }

    fun onAddressChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        addressError.set(null)
    }

    fun onCityChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        cityError.set(null)
    }

    fun onStateChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        stateError.set(null)
    }

    fun onPostalCodeChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        postalCodeError.set(null)
    }
}
