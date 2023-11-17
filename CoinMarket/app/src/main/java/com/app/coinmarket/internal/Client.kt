package com.app.coinmarket.internal

import android.content.Context
import com.app.coinmarket.api.ServerUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object Client {

    operator fun invoke(context: Context,interceptor: NetworkInterceptor): OkHttpClient {


        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
             val okHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
                 val newRequest: Request = chain.request().newBuilder()
                     .header("X-CMC_PRO_API_KEY", ServerUrl.API_KEY)
                 .build()
             chain.proceed(newRequest)
             })
            .connectTimeout(6000, TimeUnit.SECONDS)
            .callTimeout(6000, TimeUnit.SECONDS)
            .readTimeout(6000, TimeUnit.SECONDS)
            .writeTimeout(6000, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        return okHttpClient
    }


}