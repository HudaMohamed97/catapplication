package com.example.catapplication.fragments.PatientsFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.catapplication.R
import com.example.catapplication.models.Doctors
import com.toptoche.searchablespinnerlibrary.SearchableSpinner

class PatientsFragment : Fragment() {

    private lateinit var root: View
    private lateinit var patientViewModel: PatientViewModel
    private lateinit var spinner: SearchableSpinner
    private var selectedDoctorId = 0
    lateinit var shared: SharedPreferences
    private val doctorsList = arrayListOf<Doctors>()
    private val doctorsNameList = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.patient_fragment, container, false)
        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callDoctorsList()
        setClickListeners()
    }

    private fun setClickListeners() {
        spinner = root.findViewById(R.id.spinnerDoctors)
    }

    private fun callDoctorsList() {
        patientViewModel.getDoctorsList(getUserId()).observe(this, Observer {
            if (it != null) {
                for (doctorName in it.data) {
                    doctorsList.add(doctorName)
                }
                prepareDoctorsList(doctorsList)
            }


        })
    }


    private fun prepareDoctorsList(doctorsList: ArrayList<Doctors>) {
        for (doctor in doctorsList) {
            doctorsNameList.add(doctor.name)
        }
        initializeSpinner(spinner, doctorsNameList)


    }

    private fun getUserId(): Int {
        shared = activity!!.getSharedPreferences("id", Context.MODE_PRIVATE)
        return shared.getInt("id", 0)
    }

    private fun initializeSpinner(spinner: SearchableSpinner, doctorsNameList: ArrayList<String>) {
        val arrayAdapter =
            context?.let { ArrayAdapter(it, R.layout.spinner_item, doctorsNameList) }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                selectedDoctorId = doctorsList[position].id
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }


        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinner.adapter = arrayAdapter
        }
    }

}