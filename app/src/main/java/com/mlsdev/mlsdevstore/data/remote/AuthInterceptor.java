package com.mlsdev.mlsdevstore.data.remote;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request origin = chain.request();
        Request.Builder requestBuilder = origin.newBuilder()
                .header("X-EBAY-API-APP-ID", "SerhiiPe-MLSDevSt-SBX-d5d7a0307-2b7c5f29")
                .header("X-EBAY-API-SITE-ID", "0")
                .header("X-EBAY-API-CALL-NAME", "FindProducts")
                .header("X-EBAY-API-VERSION", "863")
                .header("X-EBAY-API-REQUEST-ENCODING", "xml");

        return chain.proceed(requestBuilder.build());
    }
}
