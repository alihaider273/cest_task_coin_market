package com.app.coinmarket.model

data class Quote(
    var USD: Quote? = null,
    var volume_change_24h: Double = 0.0,
    var price: Double = 0.0
)
