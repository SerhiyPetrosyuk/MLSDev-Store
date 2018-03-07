package com.mlsdev.mlsdevstore.presentaion.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.ActivityEditPersonalInfoBinding;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;

public class EditPersonalInfoActivity extends BaseActivity {
    private ActivityEditPersonalInfoBinding binding;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, EditPersonalInfoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_personal_info);
        initToolbar(binding.toolbar);
        displayBackArrow(true);
    }
}
