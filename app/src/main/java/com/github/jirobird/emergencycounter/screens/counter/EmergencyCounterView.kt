package com.github.jirobird.emergencycounter.screens.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.jirobird.emergencycounter.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class EmergencyCounterView:Fragment(),
    EmergencyCounterContract.IEmergencyCounterView {

    private val presenter =
        EmergencyCounterPresenter()
    private lateinit var tvTimer:TextView
    private lateinit var tvPeople:TextView
    private lateinit var bAddPeople: ExtendedFloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val newView = inflater.inflate(R.layout.screen_emergency_counter,container, false)

        if (newView != null){
            tvTimer = newView.findViewById(R.id.tv_timer)
            tvPeople = newView.findViewById(R.id.tv_people)
            bAddPeople = newView.findViewById(R.id.b_add_human)
            bAddPeople.setOnClickListener {
                presenter.addPeopleButtonPressed()
            }

            presenter.view = this
            return newView
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.viewIsReady()
    }

    override fun setTimerValueToUI(value:String){
        tvTimer.text = value
    }

    override fun setPeopleValueToUI(value: String){
        tvPeople.text = "Вышло $value"
    }

    fun setFileName(value:String?) {
        presenter.setFileName(value)
    }
}