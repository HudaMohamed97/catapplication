package com.example.catapplication.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        loginButtonAction()
    }

    override fun loginButtonAction() {
        val button = root.findViewById(R.id.btn_login) as Button
        button.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_updateFragment)
        }

    }
}