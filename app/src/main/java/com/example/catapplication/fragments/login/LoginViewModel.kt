package com.example.catapplication.fragments.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapplication.models.Data
import com.example.catapplication.models.DataResponse
import com.example.catapplication.network.RepositoryHelper
import com.example.catapplication.utilies.Validation


class LoginViewModel : ViewModel() {
    private var repositoryHelper: RepositoryHelper = RepositoryHelper()
    private lateinit var mutableLiveData: MutableLiveData<DataResponse>
    private lateinit var shared: SharedPreferences
    val isLoading = MutableLiveData<Boolean>()


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

    fun login(emailEt: String, passwordEt: String) {
        // isLoading.value = true
        mutableLiveData = repositoryHelper.login(emailEt, passwordEt)

    }

    fun saveData(userData: Data, context: Context) {
        shared = context.getSharedPreferences("id", Context.MODE_PRIVATE)
        val myDataHolder = shared.edit()
        myDataHolder.putInt("id", userData.id)
        Log.i("hhhh", "" + userData.id)
        myDataHolder.putString("name", userData.username)
        myDataHolder.putString("email", userData.email)
        myDataHolder.putString("account_type", userData.account_type)
        myDataHolder.putInt("target", userData.target)
        myDataHolder.putInt("score", userData.score)
        myDataHolder.putInt("percentage", userData.percentage)
        myDataHolder.putString("username", userData.username)
        myDataHolder.apply()
    }

    fun getData(): MutableLiveData<DataResponse> {
        return mutableLiveData


    }


}







