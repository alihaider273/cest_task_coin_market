package com.app.coinmarket.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.coinmarket.internal.base.Resource
import com.app.coinmarket.model.CryptoListData
import com.app.coinmarket.model.LogoModel
import com.app.coinmarket.remote.coin.CoinDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CryptoViewModel(private val coinDataSource: CoinDataSource):ViewModel() {

    var coinLogo =MutableLiveData<Resource<LogoModel>>()
    var cryptoData =MutableLiveData<Resource<CryptoListData>>()

     fun getLogo(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            coinLogo.postValue(Resource.loading())
            val response = coinDataSource.getLogo(id)
            coinLogo.postValue(response)
        }
    }
     fun getCryptocurrencies(){
        viewModelScope.launch(Dispatchers.IO) {
            cryptoData.postValue(Resource.loading())
            val response = coinDataSource.getCryptocurrencies()
            cryptoData.postValue(response)
        }
    }
}