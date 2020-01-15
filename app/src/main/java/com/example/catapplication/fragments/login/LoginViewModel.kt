package com.example.catapplication.fragments.login

import androidx.lifecycle.ViewModel
import com.example.catapplication.utilies.Validation

class LoginViewModel : ViewModel() {


    fun validateLoginInfo(
        emailEt: String,
        passwordEt: String
    ): Boolean {

        val isEmailValid = validate(emailEt)
        val isPasswordValid = validate(passwordEt)
        return !(!isEmailValid || !isPasswordValid)
    }


    fun validateEmail(emailEt: String): Boolean {
        var isValid = false
        if (emailEt.isNotEmpty()) {
            isValid = Validation.isEmailValid(emailEt)

        }
        return isValid
    }

    fun validate(passwordEt: String): Boolean {
        var isValid = true
        if (passwordEt.isEmpty()) {
            isValid = false

        }
        return isValid
    }

}







