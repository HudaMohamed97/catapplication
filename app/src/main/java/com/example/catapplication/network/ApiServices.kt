package com.example.catapplication.network

import com.example.catapplication.models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("login")
    fun login(@Body body: Map<String, String>): Call<DataResponse>

    @POST("add-patient")
    @FormUrlEncoded
    fun addPatient(@FieldMap patient: HashMap<String, String>): Call<patientAddedResponse>


    @POST("patient/{patientId}/drop")
    @FormUrlEncoded
    fun dropPatient(@Path("patientId") patientId: Int, @FieldMap map: Map<String, String>): Call<ResponseBody>


    @GET("doctor-list/{userId}")
    fun getDoctorsList(@Path("userId") userId: Int): Call<DoctorsResponse>


    @GET("user-doctor-list/{userId}")
    fun getDoctorsByUser(@Path("userId") userId: Int): Call<UserDoctorsResponse>


    @GET("regions")
    fun getRegions(): Call<List<RegionResponse>>

    @GET("reasons")
    fun getReasones(): Call<ReasonsResponce>

    @GET("products")
    fun getCmlProducts(): Call<CmlResponce>

}