package com.mlsdev.mlsdevstore.data.model.order;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GuestCheckoutSessionRequest {
    @SerializedName("contactEmail")
    private String contactEmail;
    @SerializedName("contactFirstName")
    private String contactFirstName;
    @SerializedName("contactLastName")
    private String contactLastName;
    @SerializedName("creditCard")
    private CreditCard creditCard;
    @SerializedName("lineItemInputs")
    private List<LineItemInput> lineItemInputs = new ArrayList<>();
    @SerializedName("shippingAddress")
    private Address shippingAddress;

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public List<LineItemInput> getLineItemInputs() {
        return lineItemInputs;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public void setLineItemInputs(List<LineItemInput> lineItemInputs) {
        this.lineItemInputs = lineItemInputs;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
