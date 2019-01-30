package com.mlsdev.mlsdevstore.data.model.order

import com.google.gson.annotations.SerializedName
import com.mlsdev.mlsdevstore.data.model.user.Address
import com.mlsdev.mlsdevstore.data.model.user.CreditCard

data class GuestCheckoutSessionRequest(
        @SerializedName("creditCard")
        var creditCard: CreditCard,
        @SerializedName("lineItemInputs")
        val lineItemInputs: List<LineItemInput>,
        @SerializedName("shippingAddress")
        val shippingAddress: Address
)