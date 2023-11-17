package com.app.coinmarket.model

data class CryptoListData(
    var quote: Quote? = null,
    var name: String? = null, var symbol: String? = null,
    var data: List<CryptoListData> = ArrayList(),
    var id: Int = 0
) {
    fun getPrice(): Double {
        return quote?.USD?.price!!
    }

    fun getVolumeChange(): Double {
        return quote?.USD?.volume_change_24h!!
    }
}

