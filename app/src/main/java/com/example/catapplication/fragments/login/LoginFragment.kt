package com.example.catapplication.fragments.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.catapplication.R


class LoginFragment : Fragment(), LoginInterface {
    private lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.login_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }


    override fun setClickListeners() {
        val mainLayout = root.findViewById(R.id.mainLayout) as View
        val button = root.findViewById(R.id.btn_login) as Button

        mainLayout.setOnClickListener {
            hideKeyboard()
        }
        button.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_updateFragment)
        }
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus

        if (view != null) {
            val imm =
                context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}