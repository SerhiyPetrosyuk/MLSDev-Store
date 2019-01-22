package com.mlsdev.mlsdevstore.presentaion.account

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.mlsdev.mlsdevstore.data.local.LocalDataSource
import com.mlsdev.mlsdevstore.presentaion.utils.*
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class EditAccountViewModel @Inject
constructor(private val localDataSource: LocalDataSource, private val fieldsValidator: FieldsValidator) : BaseViewModel() {
    val accountUpdated = CustomObservableBoolean()
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
    }

    fun onSubmitPersonalInfoClick() {
        compositeDisposable.add(fieldsValidator
                .putField(EMAIL, Objects.requireNonNull<String>(email.get()))
                .putField(FIRST_NAME, Objects.requireNonNull<String>(firstName.get()))
                .putField(LAST_NAME, Objects.requireNonNull<String>(lastName.get()))
                .validateFields()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { this.updatePersonalInfo() },
                        { throwable ->
                            val error = throwable as FieldsValidator.ValidationError
                            if (error.invalidFields.containsKey(EMAIL))
                                emailError.set(error.invalidFields[EMAIL])

                            if (error.invalidFields.containsKey(FIRST_NAME))
                                firstNameError.set(error.invalidFields[FIRST_NAME])

                            if (error.invalidFields.containsKey(LAST_NAME))
                                lastNameError.set(error.invalidFields[LAST_NAME])
                        }))
    }

    internal fun updatePersonalInfo() {
        localDataSource.updatePersonalInfo(email.get(), firstName.get(), lastName.get())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        Log.d(BaseViewModel.LOG_TAG, "Personal info was updated")
                        accountUpdated.set(true)
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
}
