package com.mlsdev.mlsdevstore.presentaion.viewmodel;

import android.databinding.ObservableField;

public class BaseHeaderViewModel {

    public ObservableField<String> header = new ObservableField<>();

    public void setHeader(String header) {
        this.header.set(header);
    }

}
