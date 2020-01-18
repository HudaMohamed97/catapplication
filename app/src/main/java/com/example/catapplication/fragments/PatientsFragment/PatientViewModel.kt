package com.example.catapplication.fragments.PatientsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapplication.models.PatientModelResponce
import com.example.catapplication.models.UserDoctorsResponse
import com.example.catapplication.network.AddFragmentRepository
import okhttp3.ResponseBody

class PatientViewModel : ViewModel() {
    private var addFragmentRepository: AddFragmentRepository = AddFragmentRepository()

    fun getDoctorsList(userID: Int): MutableLiveData<UserDoctorsResponse> {
        return addFragmentRepository.getDoctorsListByUser(userID)
    }

    fun getPatientsList(userID: Int): MutableLiveData<PatientModelResponce> {
        return addFragmentRepository.loadPatientsDataByUser(userID, 1)
    }

    fun getPatientsListByDoctor(userID: Int): MutableLiveData<ResponseBody> {
        return addFragmentRepository.loadPatientsDataOfDoctorByPage(userID, 1)
    }
}