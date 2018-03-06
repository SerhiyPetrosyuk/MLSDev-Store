package com.mlsdev.mlsdevstore.data.model.order;

import com.google.gson.annotations.SerializedName;

public class CreditCard {
    @SerializedName("brand")
    private String brand;
    @SerializedName("cardNumber")
    private String cardNumber;
    @SerializedName("cvvNumber")
    private String cvvNumber;
    @SerializedName("expireMonth")
    private int expireMonth;
    @SerializedName("expireYear")
    private int expireYear;
    @SerializedName("billingAddress")
    private Address billingAddress;

    public CreditCard() {
    }

    public String getBrand() {
        return brand;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvvNumber() {
        return cvvNumber;
    }

    public int getExpireMonth() {
        return expireMonth;
    }

    public int getExpireYear() {
        return expireYear;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public void setExpireMonth(int expireMonth) {
        this.expireMonth = expireMonth;
    }

    public void setExpireYear(int expireYear) {
        this.expireYear = expireYear;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}
