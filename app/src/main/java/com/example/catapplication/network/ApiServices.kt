package com.example.catapplication.network

import com.example.catapplication.models.DataResponse
import com.example.catapplication.models.DoctorsResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("login")
    fun login(@Body body: Map<String, String>): Call<DataResponse>

    @POST("add-patient")
    @FormUrlEncoded
    fun addPatient(@FieldMap map: Map<String, String>): Call<ResponseBody>

    @GET("doctor-list/{userId}")
    fun getDoctorsList(@Path("userId") userId: Int): Call<DoctorsResponse>

}