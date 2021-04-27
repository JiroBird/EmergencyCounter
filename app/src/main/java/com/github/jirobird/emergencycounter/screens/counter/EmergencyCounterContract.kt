package com.github.jirobird.emergencycounter.screens.counter

import com.github.jirobird.mvp.MvpPresenter
import com.github.jirobird.mvp.MvpView

interface EmergencyCounterContract {
    interface IEmergencyCounterView: MvpView {
        fun setTimerValueToUI(value:String)
        fun setPeopleValueToUI(value: String)
    }

    interface IEmergencyCounterPresenter:
        MvpPresenter<IEmergencyCounterView> {
        fun addPeopleButtonPressed()
        fun setFileName(value: String?)
    }
}