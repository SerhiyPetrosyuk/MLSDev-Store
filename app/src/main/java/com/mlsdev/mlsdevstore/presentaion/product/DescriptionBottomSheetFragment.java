package com.mlsdev.mlsdevstore.presentaion.product;

import android.annotation.SuppressLint;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.FragmentBottomSheetBinding;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;

public class DescriptionBottomSheetFragment extends BottomSheetDialogFragment {

    public DescriptionBottomSheetFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle data = getArguments();
        FragmentBottomSheetBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_bottom_sheet,
                container,
                false);

        if (data != null && data.containsKey(ExtrasKeys.PRODUCT_DESCRIPTION)) {
            binding.description.getSettings().setJavaScriptEnabled(true);
            binding.description.loadDataWithBaseURL("", data.getString(ExtrasKeys.PRODUCT_DESCRIPTION), "text/html", "UTF-8", "");
        }

        return binding.getRoot();
    }
}
