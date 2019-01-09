package com.mlsdev.mlsdevstore.presentaion.account;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.FragmentAccountBinding;
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment;

public class AccountFragment extends BaseFragment {
    private FragmentAccountBinding binding;
    private AccountViewModel accountViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        accountViewModel = ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel.class);
        binding.setViewModel(accountViewModel);
        getLifecycle().addObserver(accountViewModel);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getLifecycle().removeObserver(accountViewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.navigation_title_account);
    }

}
