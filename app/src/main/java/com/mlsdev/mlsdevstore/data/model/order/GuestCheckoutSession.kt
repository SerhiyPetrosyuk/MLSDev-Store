package com.mlsdev.mlsdevstore.data.model.order

import com.google.gson.annotations.SerializedName

data class GuestCheckoutSession(
        @SerializedName("checkoutSessionId")
        val checkoutSessionId: String
)