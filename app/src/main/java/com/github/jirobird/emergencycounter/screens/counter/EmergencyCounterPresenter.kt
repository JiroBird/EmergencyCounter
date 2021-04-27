package com.github.jirobird.emergencycounter.screens.counter

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import com.github.jirobird.emergencycounter.models.IOFileStream
import com.github.jirobird.mvp.BasePresenter
import java.util.*

class EmergencyCounterPresenter: BasePresenter<EmergencyCounterContract.IEmergencyCounterView>(),
    EmergencyCounterContract.IEmergencyCounterPresenter {
    private var startCounter: Long = 0
    private var peopleCount:Long = 0

    private var fileName: String? = null
    private var context:Context? = null

    private lateinit var ioFileStream: IOFileStream

    override fun viewIsReady() {
        startCounter = Date().time
        startTimerTick()
        view?.setPeopleValueToUI("$peopleCount")

        context = (view as Fragment).context
        context?.let {
            ioFileStream = IOFileStream(it)
        }
    }

    override fun destroy() {
        super.destroy()
        synchronized(this){
            running = false
        }
    }

    override fun setFileName(value: String?) {
        fileName = value
    }

    override fun addPeopleButtonPressed() {
        peopleCount += 1
        view?.setPeopleValueToUI("$peopleCount")
        pushDataToFile()
    }

    private fun pushDataToFile() {
        val tickCounter = Date().time - startCounter
        val date = Date().time
        fileName?.let {
            ioFileStream.appendToFileData(it, "$date;$tickCounter")
        }
    }

    private fun startTimerTick(){
        running = true
        Handler(Looper.getMainLooper()).postDelayed(timerTicker, 0)
    }

    private var running = false
    private val timerTicker = object : Runnable {
        override fun run() {
            val tickCounter = (Date().time - startCounter) / 1000

            val hour = tickCounter / 3600
            val min = (tickCounter - hour * 3600) / 60
            val sec = tickCounter % 60

            val readableValue = String.format("%02d:%02d:%02d", hour, min, sec)
            view?.setTimerValueToUI(readableValue)
            if (running) {
                Handler(Looper.getMainLooper()).postDelayed(this, 1000)
            }
        }
    }
}