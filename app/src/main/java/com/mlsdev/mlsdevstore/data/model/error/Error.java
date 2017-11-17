package com.mlsdev.mlsdevstore.data.model.error;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Parameter;
import java.util.List;

public class Error {
    @SerializedName("errorId")
    private Integer errorId;
    @SerializedName("domain")
    private String domain;
    @SerializedName("subDomain")
    private String subDomain;
    @SerializedName("category")
    private String category;
    @SerializedName("message")
    private String message;
    @SerializedName("longMessage")
    private String longMessage;
    @SerializedName("inputRefIds")
    private List<String> inputRefIds;
    @SerializedName("outputRefIds")
    private List<String> outputRefIds;
    @SerializedName("parameters")
    private List<Parameter> parameters;

    public Integer getErrorId() {
        return errorId;
    }

    public String getDomain() {
        return domain;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public String getCategory() {
        return category;
    }

    public String getMessage() {
        return message;
    }

    public String getLongMessage() {
        return longMessage;
    }

    public List<String> getInputRefIds() {
        return inputRefIds;
    }

    public List<String> getOutputRefIds() {
        return outputRefIds;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
