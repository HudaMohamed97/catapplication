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
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catapplication.R
import com.example.catapplication.models.Doctors
import com.example.catapplication.models.PatientRepData
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.patient_fragment.*

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
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    var connecttionType = 1
    var lastPageNum: Int = 0
    var lastPageNumPatient: Int = 0
    private var storedSelctedDector = 0
    private lateinit var myDataHolder: SharedPreferences.Editor


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
        shared = activity!!.getSharedPreferences("id", Context.MODE_PRIVATE)
        initRecyclerView()
        callDoctorsList()
        callPatientsList(1, false)
        val index = shared.getInt("storedSelctedDector", 0)
    }

    private fun setClickListeners() {
        spinner = root.findViewById(R.id.spinnerDoctors)
        recyclerView = root.findViewById(R.id.patientsRecycler)
        val button = root.findViewById(R.id.back) as ImageView
        button.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun callDoctorsList() {
        patientViewModel.getDoctorsList(getUserId()).observe(this, Observer {
            nopatienttext.visibility = View.GONE
            if (it != null) {
                for (doctor in it.data) {
                    doctorsList.add(doctor)
                }
                prepareDoctorsList(doctorsList)
            }


        })
    }

    private fun callPatientsList(pageId: Int, fromLoadMore: Boolean) {
        if (fromLoadMore) {
            loading.visibility = View.VISIBLE
        } else {
            showLoader()
        }
        patientViewModel.getPatientsList(getUserId(), pageId).observe(this, Observer {
            nopatienttext.visibility = View.GONE
            if (fromLoadMore) {
                loading.visibility = View.GONE
            } else {
                hideLoader()
            }
            if (it != null) {
                if (!fromLoadMore) {
                    allPatientsList.clear()
                }
                lastPageNum = it.patientData.last_page
                for (data in it.patientData.data) {
                    allPatientsList.add(data)
                }
                adapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++
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

    private fun callPatientsListByDoctor(selectedDoctorId: Int, pageId: Int, loadMore: Boolean) {
        if (loadMore) {
            loading.visibility = View.VISIBLE
        } else {
            showLoader()
        }
        patientViewModel.getPatientsListByDoctor(selectedDoctorId, pageId).observe(this, Observer {
            if (loadMore) {
                loading.visibility = View.GONE
            } else {
                hideLoader()
            }
            if (it != null) {
                if (!loadMore) {
                    allPatientsList.clear()
                }
                if (it.state == 1) {
                    nopatienttext.visibility = View.GONE
                    lastPageNumPatient = it.patientData.last_page
                    for (data in it.patientData.data) {
                        allPatientsList.add(data)
                    }
                    adapter.notifyDataSetChanged()
                    mHasReachedBottomOnce = false
                    currentPageNum++
                } else {
                    adapter.notifyDataSetChanged()
                    mHasReachedBottomOnce = false
                    currentPageNum++
                    if (loadMore) {
                        nopatienttext.visibility = View.GONE
                        Toast.makeText(
                            activity,
                            " No More Patients for the Doctor",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        nopatienttext.visibility = View.VISIBLE
                        Toast.makeText(
                            activity,
                            " No Patients for the Doctor",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                nopatienttext.visibility = View.GONE
                hideLoader()
                Toast.makeText(
                    activity,
                    " there is an Network Error",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
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
        doctorsNameList.add("All Doctors")
        for (doctor in doctorsList) {
            doctorsNameList.add(doctor.name)
        }
        initializeSpinner(spinner, doctorsNameList)


    }

    private fun getUserId(): Int {
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
                if (position == 0) {
                    currentPageNum = 1
                    callPatientsList(currentPageNum, false)
                    connecttionType = 1
                    storedSelctedDector = 0
                } else {
                    storedSelctedDector = position
                    connecttionType = 2
                    currentPageNum = 1
                    selectedDoctorId = doctorsList[position - 1].id
                    callPatientsListByDoctor(selectedDoctorId, currentPageNum, false)
                }
                myDataHolder = shared.edit()
                myDataHolder.putInt("storedSelctedDector", storedSelctedDector)
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
                    val patientId = allPatientsList[position].id
                    var bundle = bundleOf("fromFragment" to "fromSwitch", "patientId" to patientId)
                    findNavController().navigate(
                        R.id.action_PatientFragment_to_SwitchFragment,
                        bundle
                    )
                } else if (viewID == R.id.delete) {
                    val patientId = allPatientsList[position].id
                    var bundle = bundleOf("fromFragment" to "fromDrop", "patientId" to patientId)
                    findNavController().navigate(
                        R.id.action_PatientFragment_to_SwitchFragment,
                        bundle
                    )
                }
            }


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        if (connecttionType == 1) {
                            if (currentPageNum <= lastPageNum)
                                callPatientsList(currentPageNum, true)
                        } else if (connecttionType == 2) {
                            if (currentPageNum <= lastPageNum)
                                callPatientsListByDoctor(selectedDoctorId, currentPageNum, true)
                        }
                    }
                }
            }
        })
    }

    override fun setOnActivityTouchListener(listener: OnActivityTouchListener?) {
        this.touchListener = listener
    }

    override fun onResume() {
        super.onResume()
        onTouchListener?.let { recyclerView.addOnItemTouchListener(it) }
    }
}