package com.mlsdev.mlsdevstore.presentaion.viewmodel;

import androidx.databinding.ObservableField;

public class BaseHeaderViewModel {

    public ObservableField<String> header = new ObservableField<>();

    public void setHeader(String header) {
        this.header.set(header);
    }

}
