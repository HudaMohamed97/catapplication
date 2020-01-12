package com.example.catapplication.network

import androidx.lifecycle.MutableLiveData
import com.example.catapplication.models.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class RepositoryHelper : RepositoryInterface {

    val apiResponse = ApiResponse()

    override fun login(email: String, password: String): MutableLiveData<ApiResponse.Response> {
        val data = MutableLiveData<ApiResponse.Response>()
        val body = mapOf(
            "email" to email,
            "password" to password
        )
        Webservice.getInstance().api.login(body).enqueue(object : Callback<ApiResponse.Response> {

            override fun onFailure(call: Call<ApiResponse.Response>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ApiResponse.Response>,
                response: Response<ApiResponse.Response>
            ) {
                apiResponse.responseCode = response.code()
                apiResponse.isResponseSuccessful = response.isSuccessful
                if (response.isSuccessful) {
                    apiResponse.isResponseSuccessful = response.body()?.state == "1"
                    if (response.body()?.state == "1")
                        try {
                            apiResponse.responseBody = response.body()!!.data as Map<String, Any>
                            data.value = response.body()
                        } catch (e: Exception) {
                        }
                }
            }
        })
        return data


    }

    override fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}