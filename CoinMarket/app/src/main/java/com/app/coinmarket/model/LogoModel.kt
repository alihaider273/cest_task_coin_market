package com.app.coinmarket.model

import com.google.gson.annotations.SerializedName

data class LogoModel(
    @SerializedName("data")
    var data: Map<String?, LogoDataModel?>? = null
)
