package com.app.coinmarket.remote.coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.coinmarket.viewmodel.CryptoViewModel

class CoinFactory(private val coinDataSource: CoinDataSource):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CryptoViewModel(coinDataSource) as T
    }
}