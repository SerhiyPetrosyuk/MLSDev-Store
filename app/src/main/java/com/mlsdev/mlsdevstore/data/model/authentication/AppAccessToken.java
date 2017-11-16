package com.mlsdev.mlsdevstore.data.model.authentication;

import com.google.gson.annotations.SerializedName;

public class AppAccessToken {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private Integer expiresIn;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expiration_date")
    private long expirationDate;

    public String getAccessToken() {
        return accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn * 1000;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }
}