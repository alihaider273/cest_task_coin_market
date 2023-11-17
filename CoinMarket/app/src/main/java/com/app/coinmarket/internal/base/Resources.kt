package com.app.coinmarket.internal.base

import okhttp3.Headers
import okhttp3.HttpUrl


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

data class Resource<out T>(val status: Status, val data: T?, val timeZone: Long?, val message: String?, val header: Headers?=null,val url :HttpUrl?=null) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T,timeZone:Long, headers: Headers? = null,url :HttpUrl): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                timeZone,
                null,
                headers
                ,url
            )
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                null,
                message

            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null,
                null
            )
        }
    }
}