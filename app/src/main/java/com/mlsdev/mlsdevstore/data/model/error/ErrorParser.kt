package com.mlsdev.mlsdevstore.data.model.error

import retrofit2.HttpException

class ErrorParser : Parser<Throwable, Error> {
    override fun parse(input: Throwable): Error = try {
        if (input is HttpException) {
            Error()
        } else {
            Error()
        }
    } catch (exp: Exception) {
        Error()
    }
}