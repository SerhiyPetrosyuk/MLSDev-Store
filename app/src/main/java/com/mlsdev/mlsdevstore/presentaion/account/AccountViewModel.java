package com.mlsdev.mlsdevstore.presentaion.account;

import android.content.Context;
import android.databinding.ObservableField;

import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class AccountViewModel extends BaseViewModel {
    private Context context;

    // Personal info
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();
    public final ObservableField<String> phoneNumber = new ObservableField<>();

    // Shipping info
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> city = new ObservableField<>();
    public final ObservableField<String> state = new ObservableField<>();
    public final ObservableField<String> zip = new ObservableField<>();

    // Payment info
    public final ObservableField<String> cardNumber = new ObservableField<>();

    @Inject
    public AccountViewModel(Context context) {
        this.context = context;
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
