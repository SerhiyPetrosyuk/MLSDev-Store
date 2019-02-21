package com.mlsdev.mlsdevstore.data.model.item

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartTotalSum(
        val value: Double
) : ListItem, Parcelable {
    override fun getId(): String = "total_sum"

    override fun getTitle(): String = ""

    override fun getImageUrl(): String = ""

    override fun getPrice(): Price {
        val price = Price()
        price.value = value
        return price
    }

    override fun getCondition(): String = ""

    override fun getParcelable(): Parcelable = this
}