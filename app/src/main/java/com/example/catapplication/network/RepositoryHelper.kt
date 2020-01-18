package com.example.catapplication.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.catapplication.models.ApiResponse
import com.example.catapplication.models.Data
import com.example.catapplication.models.DataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryHelper : RepositoryInterface {

    val apiResponse = ApiResponse()
    override fun login(email: String, password: String): MutableLiveData<Data> {
        val userData = MutableLiveData<Data>()
        val body = mapOf(
            "email" to email,
            "password" to password
        )
        Webservice.getInstance().api.login(body).enqueue(object : Callback<DataResponse> {

            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                apiResponse.responseCode = response.code()
                apiResponse.isResponseSuccessful = response.isSuccessful
                if (response.isSuccessful) {
                    apiResponse.isResponseSuccessful = response.body()?.state == 1
                    if (response.body()?.state == 1) {
                        userData.value = response.body()?.data
                        Log.i("hhhhhh", "hiiiiii" + userData.value?.email)
                    }
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
//kkkkkkkkkkk
            }
        })

        return userData

    }

    override fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}