package com.mlsdev.mlsdevstore.data.remote;

import android.util.Log;

import com.google.gson.Gson;
import com.mlsdev.mlsdevstore.data.model.error.Error;
import com.mlsdev.mlsdevstore.data.model.error.ErrorsWrapper;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class BaseObserver<T> implements SingleObserver<T> {
    public static final String LOG_TAG = "REQUEST_ERROR";
    public static final int SERVER_ERROR = 500;
    public static final int OAUTH_ERROR_ID_1001 = 1001;
    public static final int OAUTH_ERROR_ID_1100 = 1199;
    public static final int UNAUTHORIZED_ERROR_CODE_401 = 401;
    private final WeakReference<BaseViewModel> baseViewModelWeakReference;

    public BaseObserver(BaseViewModel viewModel) {
        this.baseViewModelWeakReference = new WeakReference<>(viewModel);
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        if (!baseViewModelWeakReference.isEnqueued())
            baseViewModelWeakReference.get().getCompositeDisposable().add(disposable);
    }

    @Override
    public void onSuccess(T data) {
        if (!baseViewModelWeakReference.isEnqueued()) {
            baseViewModelWeakReference.get().isLoading().set(false);
            baseViewModelWeakReference.get().isRefreshing().set(false);
        }
    }

    @Override
    public void onError(Throwable throwable) {

        if (throwable instanceof SocketTimeoutException) {
            baseViewModelWeakReference.get().onNetworkErrorOccurred();
        } else if (throwable instanceof IOException) {
            baseViewModelWeakReference.get().onTechnicalErrorOccurred();
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            if (httpException.code() == UNAUTHORIZED_ERROR_CODE_401) {
                baseViewModelWeakReference.get().onAuthorizationErrorOccurred();
            } else if (httpException.code() >= SERVER_ERROR) {
                baseViewModelWeakReference.get().onTechnicalErrorOccurred();
            } else if (httpException.response().errorBody() != null) {
                ResponseBody responseBody = httpException.response().errorBody();

                try {
                    String json = responseBody.string();
                    ErrorsWrapper errorsWrapper = new Gson().fromJson(json, ErrorsWrapper.class);
                    Error error = errorsWrapper.getErrors().get(0);

                    Log.e(LOG_TAG, error.getMessage());

                    if (error.getErrorId() >= OAUTH_ERROR_ID_1001 && error.getErrorId() <= OAUTH_ERROR_ID_1100) {
                        baseViewModelWeakReference.get().onAuthorizationErrorOccurred();
                    } else {
                        baseViewModelWeakReference.get().onCommonErrorOccurred();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        throwable.printStackTrace();
    }
}
