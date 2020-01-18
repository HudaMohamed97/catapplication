package com.example.catapplication.fragments.addFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapplication.models.*
import com.example.catapplication.network.AddFragmentRepository
import okhttp3.ResponseBody
import retrofit2.Response

class AddFragmentViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    private var addFragmentRepository: AddFragmentRepository = AddFragmentRepository()

    fun getDoctorsList(userID: Int): MutableLiveData<DoctorsResponse> {
        isLoading.value = true
        return addFragmentRepository.getDoctorsList(userID)
    }

    fun getRegionsList(): MutableLiveData<List<RegionResponse>> {
        return addFragmentRepository.getRegionsList()
    }

    fun getCmlList(): MutableLiveData<CmlResponce> {
        return addFragmentRepository.getCmlList()
    }

    fun getReasonsList(): MutableLiveData<ReasonsResponce> {
        return addFragmentRepository.getReasonsList()
    }

    fun addPatient(patient: HashMap<String, String>): MutableLiveData<patientAddedResponse> {
        return addFragmentRepository.addPatient(patient)
    }

    fun dropPatient(patientId: Int, map: HashMap<String, String>): MutableLiveData<ResponseBody> {
        return addFragmentRepository.dropPatient(patientId, map)
    }


}