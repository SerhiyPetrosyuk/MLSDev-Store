package com.mlsdev.mlsdevstore.data.remote;

import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface AuthenticationService {

    @FormUrlEncoded
    @POST("identity/v1/oauth2/token")
    Single<AppAccessToken> getAppAccessToken(@HeaderMap Map<String, String> headers,
                                             @FieldMap Map<String, String> fieldMap);

}
