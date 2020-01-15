package com.example.catapplication.models

import com.google.gson.annotations.SerializedName


data class DataResponse(
    @SerializedName("data") val data: Data,
    @SerializedName("state") val state: Int
)