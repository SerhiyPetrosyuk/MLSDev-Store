package com.mlsdev.mlsdevstore.data.remote

import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager.Key
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken

import java.io.IOException

import javax.inject.Inject

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor @Inject
constructor(private val sharedPreferencesManager: SharedPreferencesManager) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val appAccessToken = sharedPreferencesManager[Key.APPLICATION_ACCESS_TOKEN, AppAccessToken::class.java]
        var appAccessTokenString: String? = null
        if (appAccessToken != null && appAccessToken.accessToken != null)
            appAccessTokenString = appAccessToken.accessToken

        val original = chain.request()
        val request = original.newBuilder()
                .addHeader("Authorization", "Bearer $appAccessTokenString")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .method(original.method(), original.body())
                .build()

        return chain.proceed(request)
    }
}
