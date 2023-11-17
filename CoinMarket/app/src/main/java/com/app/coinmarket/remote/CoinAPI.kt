package com.app.coinmarket.remote

import com.app.coinmarket.api.ServerUrl
import com.app.coinmarket.model.CryptoListData
import com.app.coinmarket.model.LogoModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinAPI {

    @GET("v1/cryptocurrency/listings/latest?limit=20")
    suspend fun getCryptocurrencies(): Response<CryptoListData>

    @GET("v2/cryptocurrency/info?")
    suspend fun getLogo(@Query("id") id: Int): Response<LogoModel>


    companion object{
        operator fun invoke(client:OkHttpClient):CoinAPI{
            return Retrofit.Builder()
                .baseUrl(ServerUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(CoinAPI::class.java)
        }
    }
}