package com.example.catapplication.fragments.HomeFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.catapplication.R
import com.razerdp.widget.animatedpieview.AnimatedPieView
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig
import com.razerdp.widget.animatedpieview.data.SimplePieInfo
import kotlinx.android.synthetic.main.update_fragment.*
import java.text.DecimalFormat

class HomeFragment : Fragment() {
    private lateinit var root: View
    private val df2 = DecimalFormat("#.##")
    lateinit var shared: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.update_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButtonAction()
        addDropAction()
        addSwitchAction()
        initializePieChart()

    }

    private fun initializePieChart() {
        shared = activity!!.getSharedPreferences("id", Context.MODE_PRIVATE)
        val target = shared.getInt("target", 0)
        target_text.text = target.toString()

        val mAnimatedPieView = root.findViewById<AnimatedPieView>(R.id.animatedPieView)
        val config = AnimatedPieViewConfig()

        config
            .strokeMode(false)
            .floatUpDuration(1000)
            .floatDownDuration(1000)
            .splitAngle(1f)
            .duration(1000)
            .drawText(true)
            .textSize(34f)
            .pieRadius(200f)
            .pieRadiusRatio(0.8f)

        config.addData(
            SimplePieInfo(
                22.0,
                ContextCompat.getColor(context!!, R.color.red),
                "" + df2.format(80) + " %"
            )
        )
            .addData(
                SimplePieInfo(
                    55.0,
                    ContextCompat.getColor(context!!, R.color.colorAccent),
                    "" + df2.format(55) + " %"
                )
            )
        mAnimatedPieView.applyConfig(config)
        mAnimatedPieView.start()
    }

    private fun addButtonAction() {
        val button = root.findViewById(R.id.btn_Add) as Button
        button.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_AddFragment)
        }
    }

    private fun addDropAction() {
        val button = root.findViewById(R.id.btn_update) as Button
        button.setOnClickListener {
            //findNavController().navigate(R.id.action_HomeFragment_to_AddFragment)
        }
    }

    fun addSwitchAction() {
        val button = root.findViewById(R.id.btn_login) as Button
        button.setOnClickListener {
            // findNavController().navigate(R.id.action_HomeFragment_to_AddFragment)
        }
    }
}