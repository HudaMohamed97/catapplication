package com.example.catapplication.network

import androidx.lifecycle.MutableLiveData
import com.example.catapplication.models.ApiResponse

interface RepositoryInterface {

    fun login(email: String, Password: String): MutableLiveData<ApiResponse.Response>

    fun update()
}