package com.example.catapplication.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.catapplication.models.*
import okhttp3.ResponseBody
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

    fun getDoctorsListByUser(userId: Int): MutableLiveData<UserDoctorsResponse> {
        val userData = MutableLiveData<UserDoctorsResponse>()

        Webservice.getInstance().api.getDoctorsByUser(userId)
            .enqueue(object : Callback<UserDoctorsResponse> {
                override fun onResponse(
                    call: Call<UserDoctorsResponse>, response: Response<UserDoctorsResponse>
                ) {
                    if (response.isSuccessful) {

                        userData.value = response.body()
                        Log.i("hhhhhh", "hiiiiii" + userData.value?.data)

                    }
                }

                override fun onFailure(call: Call<UserDoctorsResponse>, t: Throwable) {
                    Log.i("hhhhhh", "faillll")
                }
            })
        return userData
    }

    fun getReasonsList(): MutableLiveData<ReasonsResponce> {
        val userData = MutableLiveData<ReasonsResponce>()

        Webservice.getInstance().api.getReasones()
            .enqueue(object : Callback<ReasonsResponce> {
                override fun onResponse(
                    call: Call<ReasonsResponce>, response: Response<ReasonsResponce>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.state == 1) {
                            userData.value = response.body()
                            Log.i("hhhhhh", "done")

                        }
                    }
                }

                override fun onFailure(call: Call<ReasonsResponce>, t: Throwable) {
                    Log.i("hhhhhh", "faillll")
                }
            })
        return userData
    }

    fun getRegionsList(): MutableLiveData<List<RegionResponse>> {
        val regionData = MutableLiveData<List<RegionResponse>>()

        Webservice.getInstance().api.getRegions()
            .enqueue(object : Callback<List<RegionResponse>> {
                override fun onResponse(
                    call: Call<List<RegionResponse>>, response: Response<List<RegionResponse>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            regionData.value = response.body()
                            Log.i("hhhhhh", "hiiiiii" + regionData.value?.get(0)?.name)
                        }
                    }
                }

                override fun onFailure(call: Call<List<RegionResponse>>, t: Throwable) {
                    Log.i("hhhhhh", "faillll")
                }
            })
        return regionData
    }

    fun getCmlList(): MutableLiveData<CmlResponce> {
        val cmlData = MutableLiveData<CmlResponce>()

        Webservice.getInstance().api.getCmlProducts()
            .enqueue(object : Callback<CmlResponce> {
                override fun onResponse(
                    call: Call<CmlResponce>, response: Response<CmlResponce>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            cmlData.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<CmlResponce>, t: Throwable) {
                    Log.i("hhhhhh", "faillll")
                }
            })
        return cmlData
    }

    fun addPatient(patient: HashMap<String, String>): MutableLiveData<patientAddedResponse> {
        val responseMessage = MutableLiveData<patientAddedResponse>()

        Webservice.getInstance().api.addPatient(patient)
            .enqueue(object : Callback<patientAddedResponse> {
                override fun onResponse(
                    call: Call<patientAddedResponse>, response: Response<patientAddedResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            responseMessage.value = response.body()
                            Log.i("hhhhhh", "donee")

                        }
                    }
                }

                override fun onFailure(call: Call<patientAddedResponse>, t: Throwable) {
                    Log.i("hhhhhh", "faillll")
                }
            })
        return responseMessage
    }

    fun dropPatient(
        patientId: Int,
        map: HashMap<String, String>
    ): MutableLiveData<ResponseBody> {
        val responseMessage = MutableLiveData<ResponseBody>()

        Webservice.getInstance().api.dropPatient(patientId, map)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            responseMessage.value = response.body()
                            Log.i("hhhhhh", "donee")

                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.i("hhhhhh", "faillll")
                }
            })
        return responseMessage
    }


}