package com.example.catapplication.fragments.HomeFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
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
    private var scoredPercentage: Int = 0
    private var unscoredPrecentage: Int = 0


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
        dropButtonPatientUser()
        switchButtonPatientUser()
        initializePieChart()
        val button = root.findViewById(R.id.back) as ImageView
        button.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initializePieChart() {
        shared = activity!!.getSharedPreferences("id", Context.MODE_PRIVATE)
        val target = shared.getInt("target", 0)
        val score = shared.getInt("score", 0)
        scoredPercentage = shared.getInt("percentage", 0)
        unscoredPrecentage = 100 - scoredPercentage
        val unscored = target - score
        target_text.text = target.toString()
        scoredtxt.text = score.toString()
        unscoredtxt.text = unscored.toString()
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
                unscored.toDouble(),
                ContextCompat.getColor(context!!, R.color.red),
                "" + df2.format(unscoredPrecentage) + " %"
            )
        )
            .addData(
                SimplePieInfo(
                    score.toDouble(),
                    ContextCompat.getColor(context!!, R.color.yellow),
                    "" + df2.format(scoredPercentage) + " %"
                )
            )
        mAnimatedPieView.applyConfig(config)
        mAnimatedPieView.start()
    }

    private fun addButtonAction() {
        val button = root.findViewById(R.id.btn_Add) as ImageView
        button.setOnClickListener {
            var bundle = bundleOf("fromFragment" to "fromAdd")
            findNavController().navigate(R.id.action_HomeFragment_to_AddFragment, bundle)
        }
    }

    private fun dropButtonPatientUser() {
        val dropButton = root.findViewById(R.id.btn_drop) as ImageView
        dropButton.setOnClickListener {
            var bundle = bundleOf("fromFragment" to "fromDrop")
            findNavController().navigate(R.id.action_HomeFragment_to_PatientFragment, bundle)
        }
    }

    private fun switchButtonPatientUser() {
        val dropButton = root.findViewById(R.id.btn_Switch) as ImageView
        dropButton.setOnClickListener {
            var bundle = bundleOf("fromFragment" to "fromSwitch")
            findNavController().navigate(R.id.action_HomeFragment_to_PatientFragment, bundle)
        }
    }
}