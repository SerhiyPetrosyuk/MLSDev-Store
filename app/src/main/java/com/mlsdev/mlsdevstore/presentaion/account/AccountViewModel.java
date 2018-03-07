package com.mlsdev.mlsdevstore.presentaion.account;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.databinding.ObservableField;

import com.mlsdev.mlsdevstore.data.local.LocalDataSource;
import com.mlsdev.mlsdevstore.data.model.order.GuestCheckoutSessionRequest;
import com.mlsdev.mlsdevstore.data.remote.BaseObserver;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class AccountViewModel extends BaseViewModel {
    private Context context;
    private LocalDataSource dataSource;

    // Personal info
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();

    // Shipping info
    public final ObservableField<String> phoneNumber = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> city = new ObservableField<>();
    public final ObservableField<String> state = new ObservableField<>();
    public final ObservableField<String> zip = new ObservableField<>();

    // Payment info
    public final ObservableField<String> cardNumber = new ObservableField<>();

    @Inject
    public AccountViewModel(Context context, LocalDataSource dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        dataSource.getGuestCheckoutSession()
                .subscribe(new BaseObserver<GuestCheckoutSessionRequest>(this) {
                    @Override
                    public void onSuccess(GuestCheckoutSessionRequest data) {
                        super.onSuccess(data);
                        email.set(data.getContactEmail() != null ? data.getContactEmail() : "");
                        firstName.set(data.getContactFirstName() != null ? data.getContactFirstName() : "");
                        lastName.set(data.getContactLastName() != null ? data.getContactLastName() : "");
                    }
                });
    }

    public void onEditPersonalInfoClick() {
        // TODO: 3/6/18 start personal info editing
    }

    public void onEditShippingInfoClick() {
        // TODO: 3/6/18 start shipping info editing
    }

    public void onEditPaymentInfoClick() {
        // TODO: 3/6/18 start payment info editing
    }
}
