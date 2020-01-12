package com.example.catapplication.network

import com.example.catapplication.models.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST("login")
    fun login(@Body body: Map<String, String>): Call<ApiResponse.Response>

}