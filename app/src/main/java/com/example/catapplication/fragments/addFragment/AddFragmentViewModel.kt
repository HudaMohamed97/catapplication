package com.example.catapplication.fragments.addFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapplication.models.DoctorsResponse
import com.example.catapplication.network.AddFragmentRepository

class AddFragmentViewModel : ViewModel() {
    private lateinit var addFragmentRepository: AddFragmentRepository

    init {
        addFragmentRepository = AddFragmentRepository()
    }

    fun getDoctorsList(userID: Int): MutableLiveData<DoctorsResponse> {
        return addFragmentRepository.getDoctorsList(userID)
    }


}