package com.mlsdev.mlsdevstore.data.remote;

import android.util.Log;

import com.google.gson.Gson;
import com.mlsdev.mlsdevstore.data.model.error.Error;
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
    private final WeakReference<BaseViewModel> baseViewModelWeakReference;

    public BaseObserver(BaseViewModel viewModel) {
        this.baseViewModelWeakReference = new WeakReference<>(viewModel);
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        if (!baseViewModelWeakReference.isEnqueued())
            baseViewModelWeakReference.get().compositeDisposable.add(disposable);
    }

    @Override
    public void onSuccess(T data) {

    }

    @Override
    public void onError(Throwable throwable) {

        if (throwable instanceof IOException) {
            baseViewModelWeakReference.get().onNetworkErrorOccurred();
        } else if (throwable instanceof SocketTimeoutException) {
            baseViewModelWeakReference.get().onTechnicalErrorOccurred();
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            ResponseBody responseBody = httpException.response().errorBody();

            Log.e(LOG_TAG, "Code: " + httpException.code());

            if (httpException.code() >= SERVER_ERROR) {
                baseViewModelWeakReference.get().onTechnicalErrorOccurred();
            } else if (responseBody != null) {
                try {
                    String json = responseBody.string();
                    Error error = new Gson().fromJson(json, Error.class);

                    Log.e(LOG_TAG, error.getLongMessage());

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
