package com.mlsdev.mlsdevstore.data.model.error;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorsWrapper {

    @SerializedName("errors")
    private List<Error> errors;

    public List<Error> getErrors() {
        return errors;
    }
}
