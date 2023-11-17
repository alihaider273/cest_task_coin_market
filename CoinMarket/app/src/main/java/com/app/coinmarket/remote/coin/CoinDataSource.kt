package com.app.coinmarket.remote.coin

import android.util.Log
import com.app.coinmarket.internal.base.BaseDataSource
import com.app.coinmarket.remote.CoinAPI

class CoinDataSource(private val coinApi:CoinAPI):BaseDataSource() {

    suspend fun getCryptocurrencies() = getResult {
        Log.e("hash","api calling -> coinDataSource")
        coinApi.getCryptocurrencies()
    }
    suspend fun getLogo(id:Int) = getResult {
        coinApi.getLogo(id)
    }
}