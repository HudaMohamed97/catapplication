package com.example.catapplication.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.catapplication.models.DoctorsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddFragmentRepository {

    fun getDoctorsList(userId: Int): MutableLiveData<DoctorsResponse> {
        val userData = MutableLiveData<DoctorsResponse>()

        Webservice.getInstance().api.getDoctorsList(userId)
            .enqueue(object : Callback<DoctorsResponse> {

                override fun onResponse(
                    call: Call<DoctorsResponse>, response: Response<DoctorsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.state == 1) {
                            userData.value = response.body()
                            Log.i("hhhhhh", "hiiiiii" + userData.value?.data)
                        }
                    }
                }

                override fun onFailure(call: Call<DoctorsResponse>, t: Throwable) {
                    Log.i("hhhhhh", "faillll")

                }
            })

        return userData

    }


}