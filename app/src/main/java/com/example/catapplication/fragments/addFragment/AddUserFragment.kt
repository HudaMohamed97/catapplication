package com.example.catapplication.fragments.addFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.example.catapplication.models.DoctorsHospiatalResponce
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import android.widget.AdapterView.OnItemSelectedListener


class AddUserFragment : Fragment() {

    private lateinit var root: View
    private lateinit var spinner: SearchableSpinner
    private lateinit var spinnerDose: SearchableSpinner
    private lateinit var spinnerCity: SearchableSpinner
    private lateinit var spinnerRegion: SearchableSpinner
    private lateinit var spinnerCml: SearchableSpinner
    private lateinit var doctorsSpinner: SearchableSpinner
    lateinit var shared: SharedPreferences
    private lateinit var addFragmentViewModel: AddFragmentViewModel
    private val hospitalDoctorsList = arrayListOf<DoctorsHospiatalResponce>()
    private val hospitalNameList = arrayListOf<String>()
    private val doctorsList = arrayListOf<Doctors>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_user_fragment, container, false)
        addFragmentViewModel = ViewModelProviders.of(this).get(AddFragmentViewModel::class.java)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        callDoctorsList()


    }

    private fun setViews() {
        spinner = root.findViewById(R.id.spinnerHospital)
        doctorsSpinner = root.findViewById(R.id.spinnerDoctorName)
        spinnerRegion = root.findViewById(R.id.spinnerRegion)
        spinnerCity = root.findViewById(R.id.spinnerCity)
        spinnerCml = root.findViewById(R.id.spinnerCml)
        spinnerDose = root.findViewById(R.id.spinnerDose)
    }

    private fun initializeSpinner(spinner: SearchableSpinner, hospitalNameList: ArrayList<String>) {
        val arrayAdapter =
            context?.let { ArrayAdapter(it, R.layout.spinner_item, hospitalNameList) }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {

                val hospital = parentView.getItemAtPosition(position).toString()
                //  doctorsList.addAll(hospitalDoctorsList[position].doctors)
                Log.i("hhhhh", "" + position)
                prepareDoctorsList(hospitalDoctorsList[position].doctors as ArrayList<Doctors>)
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

    private fun initializeDoctorsSpinner(
        spinner: SearchableSpinner,
        hospitalNameList: ArrayList<String>
    ) {
        val arrayAdapter =
            context?.let { ArrayAdapter(it, R.layout.spinner_item, hospitalNameList) }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {

                val doctor = parentView.getItemAtPosition(position).toString()
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

    private fun callDoctorsList() {
        addFragmentViewModel.getDoctorsList(getUserId()).observe(this, Observer {
            if (it != null) {
                for (hospitalName in it.data) {
                    hospitalDoctorsList.add(hospitalName)
                }
                prepareHospitalsList(hospitalDoctorsList)
            }
        })
    }

    private fun prepareHospitalsList(hospitaDoctorsList: ArrayList<DoctorsHospiatalResponce>) {
        for (hospital in hospitaDoctorsList) {
            hospitalNameList.add(hospital.name)
        }
        initializeSpinner(spinner, hospitalNameList)


    }

    private fun prepareDoctorsList(hospitalsDoctorsList: ArrayList<Doctors>) {
        val doctorsNameList = arrayListOf<String>()
        for (doctor in hospitalsDoctorsList) {
            doctorsNameList.add(doctor.name)
        }
        initializeDoctorsSpinner(doctorsSpinner, doctorsNameList)
    }


    private fun getUserId(): Int {
        shared = activity!!.getSharedPreferences("id", Context.MODE_PRIVATE)
        val id = shared.getInt("id", 0)
        return id
    }


}