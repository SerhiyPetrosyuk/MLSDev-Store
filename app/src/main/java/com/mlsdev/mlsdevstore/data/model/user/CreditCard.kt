package com.mlsdev.mlsdevstore.data.model.user

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

import com.google.gson.annotations.SerializedName
import com.mlsdev.mlsdevstore.data.local.database.Table

@Entity(tableName = Table.CREDIT_CARDS)
data class CreditCard(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        @SerializedName("brand")
        var brand: String? = null,
        @SerializedName("cardNumber")
        var cardNumber: String? = null,
        @SerializedName("cvvNumber")
        var cvvNumber: String? = null,
        @SerializedName("expireMonth")
        var expireMonth: Int = 0,
        @SerializedName("expireYear")
        var expireYear: Int = 0,
        @Ignore
        @SerializedName("billingAddress")
        var billingAddress: Address? = null,
        var billingAddressId: Int = 0
)
