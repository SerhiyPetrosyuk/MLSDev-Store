package com.mlsdev.mlsdevstore.presentaion.store

import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableField
import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.model.item.ListItem
import com.mlsdev.mlsdevstore.presentaion.product.ProductDetailsActivity
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys

class ProductItemViewModel {
    private var listItem: ListItem? = null
    private var cart: Cart? = null
    val title = ObservableField<String>()
    val priceFirstPart = ObservableField<String>()
    val priceSecondPart = ObservableField<String>()
    val currency = ObservableField<String>()
    val imageUrl = ObservableField<String>()
    val isNew = CustomObservableBoolean()

    fun setItem(cart: Cart?, listItem: ListItem) {
        this.cart = cart
        this.listItem = listItem

        val priceArray = listItem.price.value.toString().split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        title.set(listItem.title)
        if (priceArray.isNotEmpty()) {
            priceFirstPart.set(priceArray[0])
            if (priceArray.size == 1)
                priceArray[1] = "00"

            if (priceArray[1].length == 1)
                priceArray[1] = priceArray[1] + "0"

            priceSecondPart.set(priceArray[1])
            currency.set(listItem.price.currency)
        }
        imageUrl.set(listItem.imageUrl)
        isNew.set(listItem.condition != null && listItem.condition == Item.Condition.New)
    }

    fun onItemClick(itemView: View) {
        val bundle = Bundle()
        bundle.putParcelable(ExtrasKeys.KEY_PRODUCT_DETAILS, listItem!!.parcelable)
        ProductDetailsActivity.launch(itemView.context, bundle)
    }

    fun removeFromCart() {
        cart?.removeItem(listItem!!.id)
    }

}
