package com.example.catapplication.models

import com.google.gson.annotations.SerializedName

data class PatientModelResponce(

    @SerializedName("data") val data: List<PatientRepData>,
    @SerializedName("state") val state: Int
)