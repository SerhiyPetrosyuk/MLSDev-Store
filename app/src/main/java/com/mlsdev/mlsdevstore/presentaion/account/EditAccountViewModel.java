package com.mlsdev.mlsdevstore.presentaion.account;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableField;
import android.util.Log;

import com.mlsdev.mlsdevstore.data.local.LocalDataSource;
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo;
import com.mlsdev.mlsdevstore.data.remote.BaseObserver;
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

public class EditAccountViewModel extends BaseViewModel {
    private LocalDataSource localDataSource;
    public final CustomObservableBoolean accountUpdated = new CustomObservableBoolean();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();

    @Inject
    public EditAccountViewModel(LocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        localDataSource.getPersonalInfo()
                .subscribe(new BaseObserver<PersonalInfo>(this) {
                    @Override
                    public void onSuccess(PersonalInfo info) {
                        super.onSuccess(info);
                        email.set(info.getContactEmail() != null ? info.getContactEmail() : "");
                        firstName.set(info.getContactFirstName() != null ? info.getContactFirstName() : "");
                        lastName.set(info.getContactLastName() != null ? info.getContactLastName() : "");
                    }
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {

    }

    public void onSubmitPersonalInfoClick() {
        localDataSource.updatePersonalInfo(email.get(), firstName.get(), lastName.get())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(LOG_TAG, "Personal info was updated");
                        accountUpdated.set(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG, e.getMessage());
                    }
                });
    }
}
