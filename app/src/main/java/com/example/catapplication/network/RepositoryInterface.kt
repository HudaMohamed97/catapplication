package com.example.catapplication.network

import androidx.lifecycle.MutableLiveData
import com.example.catapplication.models.Data

interface RepositoryInterface {

    fun login(email: String, Password: String): MutableLiveData<Data>

    fun update()
}