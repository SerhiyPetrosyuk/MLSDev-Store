package com.mlsdev.mlsdevstore.data.model.authentication;

import com.google.gson.annotations.SerializedName;
import com.mlsdev.mlsdevstore.BuildConfig;

public class AppAccessTokenRequestBody {

    @SerializedName("grant_type")
    private String grantType = "client_credentials";

    @SerializedName("redirect_uri")
    private String redirectUri;

    @SerializedName("scope")
    private String scope = "https://api.ebay.com/oauth/api_scope";

    public AppAccessTokenRequestBody() {
        redirectUri = BuildConfig.REDIRECT_URI;
    }
}
