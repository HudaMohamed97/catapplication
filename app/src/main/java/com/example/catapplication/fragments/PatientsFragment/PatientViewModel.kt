package com.example.catapplication.fragments.PatientsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapplication.models.UserDoctorsResponse
import com.example.catapplication.network.AddFragmentRepository

class PatientViewModel : ViewModel() {
    private var addFragmentRepository: AddFragmentRepository = AddFragmentRepository()

    fun getDoctorsList(userID: Int): MutableLiveData<UserDoctorsResponse> {
        return addFragmentRepository.getDoctorsListByUser(userID)
    }
}