package com.mlsdev.mlsdevstore.presentaion;

import android.app.Activity;
import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.support.v7.app.AlertDialog;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class ErrorInViewHandler {
    private Context context;
    private Observable.OnPropertyChangedCallback onNetworkErrorCallback;
    private Observable.OnPropertyChangedCallback onTechnicalErrorCallback;
    private Observable.OnPropertyChangedCallback onAuthorizationErrorCallback;
    private Observable.OnPropertyChangedCallback onUnknownErrorCallback;
    private boolean closeAppAfterError = false;

    @Inject
    public ErrorInViewHandler() {
        initOnPropertyChangedCallbacks();
    }

    public void setCloseAppAfterError(boolean closeAppAfterError) {
        this.closeAppAfterError = closeAppAfterError;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void showTechnicalError() {
        showAlertDialog(context.getString(R.string.error_title_base), context.getString(R.string.error_message_tech));
    }

    public void showNetworkError() {
        showAlertDialog(context.getString(R.string.error_title_base), context.getString(R.string.error_message_network));
    }

    public void showUnknownError() {
        showAlertDialog(context.getString(R.string.error_title_base), context.getString(R.string.error_message_unknown));
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogAppCompat)
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton("Close", null);

        if (closeAppAfterError) {
            builder.setPositiveButton("Close", (dialogInterface, i) -> {
                if (context instanceof Activity)
                    ((Activity) context).finish();
            });
        }

        builder.create()
                .show();
    }

    public void subscribeNetworkErrorCallback(BaseViewModel viewModel) {
        viewModel.networkErrorOccurred.addOnPropertyChangedCallback(onNetworkErrorCallback);
    }

    public void subscribeTeckErrorCallback(BaseViewModel viewModel) {
        viewModel.technicalErrorOccurred.addOnPropertyChangedCallback(onTechnicalErrorCallback);
    }


    public void subscribeUnknownErrorCallback(BaseViewModel viewModel) {
        viewModel.unknownErrorOccurred.addOnPropertyChangedCallback(onUnknownErrorCallback);
    }

    public void subscribeAllErrorCallbacks(BaseViewModel viewModel) {
        subscribeUnknownErrorCallback(viewModel);
        subscribeTeckErrorCallback(viewModel);
        subscribeNetworkErrorCallback(viewModel);
    }

    private void initOnPropertyChangedCallbacks() {
        onNetworkErrorCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (observable.getClass().isAssignableFrom(ObservableBoolean.class))
                    showNetworkError();
            }
        };

        onTechnicalErrorCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (observable.getClass().isAssignableFrom(ObservableBoolean.class))
                    showTechnicalError();
            }
        };

        onAuthorizationErrorCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                // TODO: 19.11.17 restart the app
            }
        };

        onUnknownErrorCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (observable.getClass().isAssignableFrom(ObservableBoolean.class))
                    showUnknownError();
            }
        };
    }

}
