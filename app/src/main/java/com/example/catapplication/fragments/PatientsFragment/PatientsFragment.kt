package com.example.catapplication.fragments.PatientsFragment

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catapplication.R
import com.example.catapplication.models.Cities
import com.example.catapplication.models.Doctors
import com.example.catapplication.models.PatientRepData
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import org.json.JSONArray
import org.json.JSONObject

class PatientsFragment : Fragment(), RecyclerTouchListener.RecyclerTouchListenerHelper {
    private lateinit var root: View
    private lateinit var patientViewModel: PatientViewModel
    private lateinit var spinner: SearchableSpinner
    private lateinit var recyclerView: RecyclerView
    private var selectedDoctorId = 0
    lateinit var shared: SharedPreferences
    private val doctorsList = arrayListOf<Doctors>()
    private val allPatientsList = arrayListOf<PatientRepData>()
    private val doctorsNameList = arrayListOf<String>()
    private var onTouchListener: RecyclerTouchListener? = null
    private var touchListener: OnActivityTouchListener? = null
    private var dialog: ProgressDialog? = null
    private lateinit var adapter: Patient_adapter
    internal var mHasReachedBottomOnce = false
    internal var currentPageNum = 1
    internal var lastPageNum: Int = 0


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
        setClickListeners()
        initRecyclerView()
        callPatientsList()
        callDoctorsList()
    }

    private fun setClickListeners() {
        spinner = root.findViewById(R.id.spinnerDoctors)
        recyclerView = root.findViewById(R.id.patientsRecycler)
    }

    private fun callDoctorsList() {
        patientViewModel.getDoctorsList(getUserId()).observe(this, Observer {
            if (it != null) {
                for (doctor in it.data) {
                    doctorsList.add(doctor)
                }
                prepareDoctorsList(doctorsList)
            }


        })
    }

    private fun callPatientsList() {
        showLoader()
        patientViewModel.getPatientsList(getUserId()).observe(this, Observer {
            hideLoader()
            if (it != null) {
                allPatientsList.clear()
                for (patientData in it.data) {
                    allPatientsList.add(patientData)
                }
                adapter.notifyDataSetChanged()
            } else {
                hideLoader()
                Toast.makeText(
                    activity,
                    " there is an Error Occurs Wrong User or password",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        })
    }

    private fun callPatientsListByDoctor(selectedDoctorId: Int) {
        showLoader()
        patientViewModel.getPatientsListByDoctor(selectedDoctorId).observe(this, Observer {
            Log.i("hhhh", "selectedDoctorId" + selectedDoctorId)
            hideLoader()
            if (it != null) {
                val responseObject = JSONObject(it.string())
                val state = responseObject.getInt("state")
                if (state == 0) {
                    Toast.makeText(
                        activity,
                        responseObject.getString("data"),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val patientsObject = responseObject.getJSONObject("data")
                    val patientsArray = patientsObject.getJSONArray("data")
                    updatePatientsList(patientsArray)
                    Log.d("Response", patientsObject.toString())
                }
            }
            /* if (it != null) {
                 it.

                 allPatientsList.clear()
                 for (patientData in it) {
                     allPatientsList.add(patientData)
                 }
                 adapter.notifyDataSetChanged()
             } else {
                 hideLoader()
                 Toast.makeText(
                     activity,
                     " there is an Error Occurs Wrong User or password",
                     Toast.LENGTH_SHORT
                 )
                     .show()
             }*/

        })
    }

    fun updatePatientsList(list: JSONArray) {
        allPatientsList.clear()
        try {
            for (i in 0 until list.length()) {
                val currentobject = list.getJSONObject(i)
                val id = currentobject.getInt("id")
                val user_id = currentobject.getInt("user_id")
                val category_id = currentobject.getInt("category_id")
                val product_id = currentobject.getInt("product_id")
                val hospital_id = currentobject.getInt("hospital_id")
                val region_id = currentobject.getInt("region_id")
                val doctor_id = currentobject.getInt("doctor_id")
                val city_id = currentobject.getInt("city_id")
                val dropped = currentobject.getInt("dropped")
                val reason_id = currentobject.getString("reason_id")
                val name = currentobject.getString("doctor_name")
                val notes = currentobject.getString("notes")
                val dose = currentobject.getString("dose_name")
                val category = currentobject.getString("category_name")
                val hospital = currentobject.getString("hospital_name")
                val date = currentobject.getString("created_at")
                val product_name = currentobject.getString("product_name")
                allPatientsList.add(
                    PatientRepData(
                        id,
                        user_id,
                        category_id,
                        product_id,
                        hospital_id,
                        region_id,
                        doctor_id,
                        city_id,
                        dropped,
                        reason_id,
                        notes,
                        date,
                        category,
                        product_name,
                        dose,
                        name,
                        hospital
                    )
                )
            }
            adapter.notifyDataSetChanged()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun hideLoader() {
        dialog?.dismiss()

    }

    private fun showLoader() {
        dialog = ProgressDialog(activity)
        dialog?.setMessage("Please, Wait")
        dialog?.setCancelable(false)
        dialog?.show()
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
                callPatientsListByDoctor(selectedDoctorId)
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

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = Patient_adapter(activity, allPatientsList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        onTouchListener = RecyclerTouchListener(activity, recyclerView)
        onTouchListener!!.setClickable(object : RecyclerTouchListener.OnRowClickListener {
            override fun onRowClicked(position: Int) {
                Log.i("hhhh", "" + position)
            }

            override fun onIndependentViewClicked(independentViewID: Int, position: Int) {
                Log.i("hhhh", "" + position)

            }
        })
            .setLongClickable(
                true
            ) { }
            .setSwipeOptionViews(R.id.edit, R.id.delete)
            .setSwipeable(
                R.id.rowFG,
                R.id.rowBG
            ) { viewID, position ->
                if (viewID == R.id.edit) {
                    Log.i("hhhh", "" + viewID)
                    val patientId = allPatientsList[position].id
                    var bundle = bundleOf("fromFragment" to "fromSwitch", "patientId" to patientId)
                    findNavController().navigate(
                        R.id.action_PatientFragment_to_SwitchFragment,
                        bundle
                    )
                    Toast.makeText(activity, "clicked", Toast.LENGTH_SHORT)
                        .show()


                } else if (viewID == R.id.delete) {
                    val patientId = allPatientsList[position].id
                    Log.i("hhhh", "" + patientId)
                    var bundle = bundleOf("fromFragment" to "fromDrop", "patientId" to patientId)
                    findNavController().navigate(
                        R.id.action_PatientFragment_to_SwitchFragment,
                        bundle
                    )
                }
            }


        /* recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
             override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                 super.onScrollStateChanged(recyclerView, newState)

                 if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                     mHasReachedBottomOnce = true

                     if (currentPageNum <= lastPageNum)
                       //  callPatientsListByDoctor(selectedDoctorId, currentPageNum)


                 }
             }
         })*/
    }

    override fun setOnActivityTouchListener(listener: OnActivityTouchListener?) {
        this.touchListener = listener
    }

    override fun onResume() {
        super.onResume()
        onTouchListener?.let { recyclerView.addOnItemTouchListener(it) }
    }
}