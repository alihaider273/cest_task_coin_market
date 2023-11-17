package com.app.coinmarket.internal

import android.content.Context
import android.util.Log
import com.app.coinmarket.api.ServerUrl.API_KEY
import com.app.coinmarket.utils.Utils.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvailable(applicationContext)) {
            throw NetworkConnectionException("Check your internet connection")
        }

        var proceed: Response? = null
        val request = chain.request()

        try {

            var requestBuilder = request.newBuilder()
                .addHeader("X-CMC_PRO_API_KEY", API_KEY)
            proceed = chain.proceed(requestBuilder.build())

        } catch (ex: Exception) {
            ex.printStackTrace()
            throw NetworkConnectionException("Check your internet connection")
        }

        return proceed
    }
}