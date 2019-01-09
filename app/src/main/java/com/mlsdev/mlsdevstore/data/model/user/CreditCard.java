package com.mlsdev.mlsdevstore.data.model.user;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.mlsdev.mlsdevstore.data.local.database.Table;

@Entity(tableName = Table.CREDIT_CARDS)
public class CreditCard {

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    @Ignore
    @SerializedName("billingAddress")
    private Address billingAddress;

    private int billingAddressId;

    public CreditCard() {
    }

    public int getId() {
        return id;
    }

    public int getBillingAddressId() {
        return billingAddressId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
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
