package com.prueba.retrofit

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("status") val status:String
        )