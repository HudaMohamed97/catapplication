package com.example.catapplication.fragments.login

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.catapplication.R
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment(), LoginInterface {
    private lateinit var root: View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var email: TextInputLayout
    private lateinit var passwordEt: TextInputLayout
    private var dialog: ProgressDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.login_fragment, container, false)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        //listenToLoading()
    }

    private fun listenToLoading() {
        loginViewModel.isLoading.observe(this, Observer {
            if (it) {
                Log.i("hhhh", "showw")
                showLoader()
            } else {
                Log.i("hhhh", "hideee")
                hideLoader()
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


    override fun setClickListeners() {
        val mainLayout = root.findViewById(R.id.mainLayout) as View
        val button = root.findViewById(R.id.btn_login) as ImageView
        email = root.findViewById(R.id.email) as TextInputLayout
        passwordEt = root.findViewById(R.id.password) as TextInputLayout

        mainLayout.setOnClickListener {
            hideKeyboard()
        }
        button.setOnClickListener {
            checkErrorEnabled()
            hideKeyboard()
            if (loginViewModel.validateLoginInfo(
                    email.editText?.text.toString(),
                    passwordEt.editText?.text.toString()
                )
            ) {
                email.isErrorEnabled = false
                passwordEt.isErrorEnabled = false
                callLoginRequest()
            }

        }
    }

    private fun callLoginRequest() {
        showLoader()
        loginViewModel.login(
            email.editText?.text.toString(),
            passwordEt.editText?.text.toString()
        )
        loginViewModel.getData().observe(this, Observer {
            hideLoader()
            if (it != null) {
                if (it.state == 1) {
                    loginViewModel.saveData(it.data, activity!!)
                    findNavController().navigate(R.id.action_loginFragment_to_updateFragment)
                } else {
                    Toast.makeText(
                        activity,
                        "Wrong User or password" + it.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                hideLoader()
                Toast.makeText(
                    activity,
                    "Network Error",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        })

    }

    private fun checkErrorEnabled() {
        if (!loginViewModel.validate(email.editText?.text.toString())) {
            email.isErrorEnabled = true
            email.error = "empty email please fill it"
        } else {
            email.isErrorEnabled = false
        }
        if (!loginViewModel.validate(passwordEt.editText?.text.toString())) {
            passwordEt.isErrorEnabled = true
            passwordEt.error = "empty password please fill it"
        } else {
            passwordEt.isErrorEnabled = false
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