package com.prueba.retrofit

import android.hardware.camera2.TotalCaptureResult
import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("status") val status:String,
    @SerializedName("totalResults") val totalResult:String,
    @SerializedName("articles") val articles: List<Articles>
    )