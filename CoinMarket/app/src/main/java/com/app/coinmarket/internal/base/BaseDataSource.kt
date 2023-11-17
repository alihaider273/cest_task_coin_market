package com.app.coinmarket.internal.base

import android.util.Log
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                val timeZone= response.raw().receivedResponseAtMillis
                val headers = response.headers()
              val url=  response.raw().request.url

                if (body != null) return Resource.success(
                    body, timeZone = timeZone, headers = headers,url = url
                )
            }
            return error("${response.errorBody()?.string()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error(message)
    }
}