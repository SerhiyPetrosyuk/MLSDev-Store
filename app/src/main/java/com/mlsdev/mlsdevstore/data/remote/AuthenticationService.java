package com.mlsdev.mlsdevstore.data.remote;

import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessTokenRequestBody;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface AuthenticationService {

    @POST("identity/v1/oauth2/token")
    Single<AppAccessToken> getAppAccessToken(@HeaderMap Map<String, String> headers,
                                             @Body AppAccessTokenRequestBody body);

}
