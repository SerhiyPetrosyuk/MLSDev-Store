package com.mlsdev.mlsdevstore.presentaion.cart

import androidx.databinding.ObservableBoolean
import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.data.repository.CartProductsRepository
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import javax.inject.Inject

class CartViewModel @Inject constructor(
        val cart: Cart,
        cartProductsRepository: CartProductsRepository
) : BaseViewModel() {
    val cartIsEmpty = ObservableBoolean(true)
    val productsInCart = cartProductsRepository.getProducts()

    fun removeItem(productId: String) {
        cart.removeItem(productId)
    }
}
