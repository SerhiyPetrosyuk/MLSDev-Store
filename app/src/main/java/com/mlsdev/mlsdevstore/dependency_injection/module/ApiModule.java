package com.mlsdev.mlsdevstore.dependency_injection.module;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager;
import com.mlsdev.mlsdevstore.data.remote.AuthInterceptor;
import com.mlsdev.mlsdevstore.data.remote.service.AuthenticationService;
import com.mlsdev.mlsdevstore.data.remote.service.BrowseService;
import com.mlsdev.mlsdevstore.data.remote.service.TaxonomyService;
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
    Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return createRetrofit(client, gson);
    }

    @Provides
    @Singleton
    @Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR)
    Retrofit provideRetrofitWithoutAuthInterceptor(@Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR) OkHttpClient client,
                                                   Gson gson) {
        return createRetrofit(client, gson);
    }

    @NonNull
    private Retrofit createRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
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
    BrowseService provideBuyService(Retrofit retrofit) {
        return retrofit.create(BrowseService.class);
    }

    @Provides
    @Singleton
    AuthenticationService provideAuthenticationService(@Named(Name.WITHOUT_AUTHORIZATION_INTERCEPTOR) Retrofit retrofit) {
        return retrofit.create(AuthenticationService.class);
    }

    @Provides
    @Singleton
    TaxonomyService provideTaxonomyService(Retrofit retrofit) {
        return retrofit.create(TaxonomyService.class);
    }

    @Provides
    AuthInterceptor provideAuthInterceptor(SharedPreferencesManager sharedPreferencesManager) {
        return new AuthInterceptor(sharedPreferencesManager);
    }

}
