package com.mlsdev.mlsdevstore.presentaion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.appcompat.app.AlertDialog;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class ErrorInViewHandler {
    private Context context;
    private Observable.OnPropertyChangedCallback onNetworkErrorCallback;
    private Observable.OnPropertyChangedCallback onTechnicalErrorCallback;
    private Observable.OnPropertyChangedCallback onAuthorizationErrorCallback;
    private Observable.OnPropertyChangedCallback onCommonErrorCallback;
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

    public void showCommonError() {
        showAlertDialog(context.getString(R.string.error_title_base), context.getString(R.string.error_message_common));
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogAppCompat)
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton(context.getString(R.string.button_close), null);

        if (closeAppAfterError) {
            builder.setPositiveButton(context.getString(R.string.button_close), (dialogInterface, i) -> {
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

    public void subscribeTechErrorCallback(BaseViewModel viewModel) {
        viewModel.technicalErrorOccurred.addOnPropertyChangedCallback(onTechnicalErrorCallback);
    }

    public void subscribeCommonErrorCallback(BaseViewModel viewModel) {
        viewModel.commonErrorOccurred.addOnPropertyChangedCallback(onCommonErrorCallback);
    }

    public void subscribeAuthErrorCallback(BaseViewModel viewModel) {
        viewModel.authErrorOccurred.addOnPropertyChangedCallback(onAuthorizationErrorCallback);
    }

    public void subscribeAllErrorCallbacks(BaseViewModel viewModel, boolean withAuthCallback) {
        subscribeCommonErrorCallback(viewModel);
        subscribeTechErrorCallback(viewModel);
        subscribeNetworkErrorCallback(viewModel);

        if (withAuthCallback)
            subscribeAuthErrorCallback(viewModel);

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
                if (context instanceof Activity) {
                    Intent intent = ((Activity) context).getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(((Activity) context).getBaseContext().getPackageName());
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                }

            }
        };

        onCommonErrorCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (observable.getClass().isAssignableFrom(ObservableBoolean.class))
                    showCommonError();
            }
        };
    }

}
