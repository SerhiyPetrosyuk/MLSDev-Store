package com.mlsdev.mlsdevstore.dependency_injection.module;

import android.support.annotation.NonNull;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.remote.AuthInterceptor;
import com.mlsdev.mlsdevstore.data.remote.service.AuthenticationService;
import com.mlsdev.mlsdevstore.data.remote.service.BuyService;
import com.mlsdev.mlsdevstore.dependency_injection.Named;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    public @interface Name {
        String WITHOUT_AUTHORIZATION_INTERCEPTOR = "with_authorization_interceptor";
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return createRetrofit(client);
    }

    @Provides
    @Singleton
    @Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR)
    Retrofit provideRetrofitWithoutAuthInterceptor(@Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR) OkHttpClient client) {
        return createRetrofit(client);
    }

    @NonNull
    private Retrofit createRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(AuthInterceptor authInterceptor) {
        OkHttpClient.Builder builder = createOkHttpClientBuilder();
        builder.addInterceptor(authInterceptor);
        return builder.build();
    }

    @Provides
    @Singleton
    @Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR)
    OkHttpClient provideOkHttpClientWithoutAuthInterceptor() {
        return createOkHttpClientBuilder().build();
    }

    @NonNull
    private OkHttpClient.Builder createOkHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder;
    }

    @Provides
    @Singleton
    BuyService provideBuyService(Retrofit retrofit) {
        return retrofit.create(BuyService.class);
    }

    @Provides
    @Singleton
    AuthenticationService provideAuthenticationService(@Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR) Retrofit retrofit) {
        return retrofit.create(AuthenticationService.class);
    }

    @Provides
    AuthInterceptor provideAuthInterceptor() {
        return new AuthInterceptor();
    }

}
