package com.mlsdev.mlsdevstore.presentaion.cart;

import androidx.databinding.ObservableField;

import java.text.DecimalFormat;

public class TotalSumItemViewModel {
    public final ObservableField<String> totalSum = new ObservableField<>("00.00");

    public TotalSumItemViewModel(double totalSum) {
        setTotalSum(totalSum);
    }

    public void setTotalSum(double totalSum) {
        this.totalSum.set(new DecimalFormat("#0.00").format(totalSum));
    }
}
