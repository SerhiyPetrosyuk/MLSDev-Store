package com.mlsdev.mlsdevstore.data.remote.service

import com.mlsdev.mlsdevstore.data.model.order.GuestCheckoutSession
import com.mlsdev.mlsdevstore.data.model.order.GuestCheckoutSessionRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {

    @POST("buy/order/v1/checkout_session/initiate")
    fun initiateGuestCheckoutSession(@Body body: GuestCheckoutSessionRequest): Single<GuestCheckoutSession>

}