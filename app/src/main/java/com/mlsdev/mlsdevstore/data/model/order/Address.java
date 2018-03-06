package com.mlsdev.mlsdevstore.data.model.order;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("addressLine1")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("county")
    private String county;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("postalCode")
    private String postalCode;
    @SerializedName("recipient")
    private String recipient;
    @SerializedName("stateOrProvince")
    private String state;

    public Address() {
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCounty() {
        return county;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getState() {
        return state;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setState(String state) {
        this.state = state;
    }
}
