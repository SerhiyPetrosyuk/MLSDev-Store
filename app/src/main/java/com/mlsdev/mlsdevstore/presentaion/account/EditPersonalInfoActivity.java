package com.mlsdev.mlsdevstore.presentaion.account;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.ActivityEditPersonalInfoBinding;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean;

public class EditPersonalInfoActivity extends BaseActivity {
    private ActivityEditPersonalInfoBinding binding;
    private EditAccountViewModel viewModel;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, EditPersonalInfoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_personal_info);
        initViewModel();
        initToolbar(binding.toolbar);
        displayBackArrow(true);
    }

    @Override
    protected void onDestroy() {
        getLifecycle().removeObserver(viewModel);
        viewModel.accountUpdated.removeOnPropertyChangedCallback(accountUpdatedCallback);
        super.onDestroy();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditAccountViewModel.class);
        viewModel.accountUpdated.addOnPropertyChangedCallback(accountUpdatedCallback);
        binding.setViewModel(viewModel);
        getLifecycle().addObserver(viewModel);
    }

    private Observable.OnPropertyChangedCallback accountUpdatedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if (observable instanceof CustomObservableBoolean && ((CustomObservableBoolean) observable).get())
                finish();
        }
    };
}
