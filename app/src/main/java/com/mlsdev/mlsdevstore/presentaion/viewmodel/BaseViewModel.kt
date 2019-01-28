package com.mlsdev.mlsdevstore.presentaion.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mlsdev.mlsdevstore.data.DataLoadState
import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.model.error.ErrorParser
import com.mlsdev.mlsdevstore.data.model.error.ValidationException
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    val compositeDisposable = CompositeDisposable()
    @Deprecated("use technicalErrorLiveData")
    val technicalErrorOccurred = ObservableBoolean()
    @Deprecated("use networkErrorLiveData")
    val networkErrorOccurred = ObservableBoolean()
    @Deprecated("use commonErrorLiveData")
    val commonErrorOccurred = ObservableBoolean()
    @Deprecated("use authErrorLiveData")
    val authErrorOccurred = ObservableBoolean()
    val technicalErrorLiveData = MutableLiveData<Boolean>()
    val networkErrorLiveData = MutableLiveData<Boolean>()
    val commonErrorLiveData = MutableLiveData<Boolean>()
    val authErrorLiveData = MutableLiveData<Boolean>()
    val isRefreshing = ObservableBoolean()
    val isLoading = CustomObservableBoolean()
    protected var context: Context? = null
    protected var dataSource: DataSource? = null
    protected var utils: Utils? = null
    private val contentLoadingSubscription = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        context = null
    }

    protected fun observeContentLoadingState(statePublisher: PublishSubject<DataLoadState>) {
        contentLoadingSubscription.add(statePublisher.subscribe {
            val loading = it == DataLoadState.LOADING
            isRefreshing.set(isRefreshing.get() and loading)
            isLoading.set(isLoading.get() and loading)
        })
    }

    protected val errorConsumer = Consumer<Throwable> {
        handleError(it)
    }

    fun onTechnicalErrorOccurred() {
        technicalErrorOccurred.set(true)
        technicalErrorLiveData.postValue(true)
        setIsRefreshing(false)
        setIsLoading(false)
    }

    fun onNetworkErrorOccurred() {
        networkErrorOccurred.set(true)
        networkErrorLiveData.postValue(true)
        setIsRefreshing(false)
        setIsLoading(false)
    }

    fun onCommonErrorOccurred() {
        commonErrorOccurred.set(true)
        commonErrorLiveData.postValue(true)
        setIsRefreshing(false)
        setIsLoading(false)
    }

    fun onAuthorizationErrorOccurred() {
        authErrorOccurred.set(true)
        authErrorLiveData.postValue(true)
        setIsRefreshing(false)
        setIsLoading(false)
    }

    protected fun setIsRefreshing(isRefreshing: Boolean) {
        this.isRefreshing.set(isRefreshing)
        this.isRefreshing.notifyChange()
    }

    protected fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
        this.isLoading.notifyChange()
    }

    companion object {
        const val LOG_TAG = "ViewModel.Log.Tag"
        const val HTTP_ERROR_CODE_401 = 401
        const val HTTP_SERVER_ERROR_500 = 500
        const val HTTP_SERVER_ERROR_504 = 504
        const val OAUTH_ERROR_ID_1001 = 1001
        const val OAUTH_ERROR_ID_1100 = 1199
    }

    fun handleError(error: Throwable) {
        isLoading.set(false)
        isRefreshing.set(false)

        when (error) {
            is ValidationException -> handleFieldsErrors(error)
            is SocketTimeoutException -> onNetworkErrorOccurred()
            is IOException -> onTechnicalErrorOccurred()
            is HttpException -> {
                when {
                    error.code() == HTTP_ERROR_CODE_401 -> onAuthorizationErrorOccurred()
                    error.code() == HTTP_SERVER_ERROR_500 or HTTP_SERVER_ERROR_504 -> onTechnicalErrorOccurred()
                    error.response().body() != null -> {
                        val errorsWrapper = ErrorParser().parse(error)
                        errorsWrapper.errors?.getOrNull(0)?.let { parsedError ->
                            Log.d(LOG_TAG, parsedError.message)
                            when (parsedError.errorId) {
                                in OAUTH_ERROR_ID_1001..OAUTH_ERROR_ID_1100 -> onAuthorizationErrorOccurred()
                                else -> onCommonErrorOccurred()
                            }
                        }
                        onCommonErrorOccurred()
                    }
                    else -> onCommonErrorOccurred()
                }
            }
            else -> onCommonErrorOccurred()
        }
    }

    protected open fun handleFieldsErrors(error: ValidationException) {

    }

    fun checkNetworkConnection(utils: Utils, onSuccessCallback: () -> Unit) {
        when (utils.isNetworkAvailable) {
            true -> onSuccessCallback()
            else -> onNetworkErrorOccurred()
        }
    }

}
