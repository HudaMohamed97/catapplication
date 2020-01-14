package com.example.catapplication.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.dev.materialspinner.MaterialSpinner
import com.example.catapplication.R


class AddUserFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var root: View
    private var list_of_items = arrayOf("Select Country", "USA", "Japan", "India")
    private lateinit var spinner: MaterialSpinner
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_user_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinner = root.findViewById(R.id.material_spinner)
        spinner.getSpinner().onItemSelectedListener = this
        val aa =
            context?.let { ArrayAdapter(it, R.layout.spinner_item, list_of_items) }
        aa?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        if (aa != null) {
            spinner.setAdapter(aa)
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        // use position to know the selected item
        if (position == 0) {
            spinner.setError("Please select Country")
        } else {
            spinner.setErrorEnabled(false)
            spinner.setLabel("COUNTRY")
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }


}