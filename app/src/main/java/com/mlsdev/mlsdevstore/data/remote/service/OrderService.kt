package com.mlsdev.mlsdevstore.data.remote.service

import retrofit2.http.POST

interface OrderService {

    @POST("")
    fun initiateGuestCheckoutSession()

}