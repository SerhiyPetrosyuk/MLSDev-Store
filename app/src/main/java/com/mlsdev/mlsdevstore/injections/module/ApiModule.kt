package com.mlsdev.mlsdevstore.injections.module

import com.google.gson.Gson
import com.mlsdev.mlsdevstore.BuildConfig
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager
import com.mlsdev.mlsdevstore.data.remote.AuthInterceptor
import com.mlsdev.mlsdevstore.data.remote.service.AuthenticationService
import com.mlsdev.mlsdevstore.data.remote.service.BrowseService
import com.mlsdev.mlsdevstore.data.remote.service.OrderService
import com.mlsdev.mlsdevstore.data.remote.service.TaxonomyService
import com.mlsdev.mlsdevstore.injections.Named
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    annotation class Name {
        companion object {
            const val WITHOUT_AUTHORIZATION_INTERCEPTOR = "with_authorization_interceptor"
        }
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return createRetrofit(client, gson)
    }

    @Provides
    @Singleton
    @Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR)
    fun provideRetrofitWithoutAuthInterceptor(@Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR) client: OkHttpClient,
                                                       gson: Gson): Retrofit {
        return createRetrofit(client, gson)
    }

    private fun createRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val builder = createOkHttpClientBuilder()
        builder.addInterceptor(authInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    @Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR)
    fun provideOkHttpClientWithoutAuthInterceptor(): OkHttpClient {
        return createOkHttpClientBuilder().build()
    }

    private fun createOkHttpClientBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder
    }

    @Provides
    @Singleton
    fun provideBuyService(retrofit: Retrofit): BrowseService {
        return retrofit.create(BrowseService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(@Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR) retrofit: Retrofit): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

    @Provides
    @Singleton
    fun provideTaxonomyService(retrofit: Retrofit): TaxonomyService {
        return retrofit.create(TaxonomyService::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderService(retrofit: Retrofit): OrderService {
        return retrofit.create(OrderService::class.java)
    }

    @Provides
    fun provideAuthInterceptor(sharedPreferencesManager: SharedPreferencesManager): AuthInterceptor {
        return AuthInterceptor(sharedPreferencesManager)
    }

}
