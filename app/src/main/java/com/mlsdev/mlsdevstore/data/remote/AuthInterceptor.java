package com.mlsdev.mlsdevstore.data.remote;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request origin = chain.request();
        Request.Builder requestBuilder = origin.newBuilder()
                .addHeader("X-EBAY-API-APP-ID", "SerhiiPe-MLSDevSt-SBX-d5d7a0307-2b7c5f29")
                .addHeader("X-EBAY-API-SITE-ID", "0")
                .addHeader("X-EBAY-API-VERSION", "863")
                .addHeader("X-EBAY-API-REQUEST-ENCODING", "xml");

        return chain.proceed(requestBuilder.build());
    }
}
