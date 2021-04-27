package com.github.jirobird.emergencycounter.screens.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.jirobird.emergencycounter.models.IOFileStream
import com.github.jirobird.mvp.BasePresenter
import java.util.*

class MainPresenter:
    BasePresenter<MainContract.IMainView>(),
    MainContract.IMainPresenter {

    private var context:Context? = null
    private lateinit var ioFileStream: IOFileStream
    private var idle = true

    override fun viewIsReady() {
        context = this.view as Context
        context?.let {
            ioFileStream = IOFileStream(it)
        }

        requestPermission()
    }

    private fun requestPermission(){
        if (ContextCompat.checkSelfPermission(context as Activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //TODO: show UI
                ActivityCompat.requestPermissions(context as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000)
            } else {
                ActivityCompat.requestPermissions(context as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000)
            }
        } else {
            didPermissionGranted(true)
        }
    }

    override fun didPermissionGranted(granted:Boolean){
        if (!granted) {
            view?.disableUIToContinue()
        } else {
            initUIFromFileList()
        }
    }

    override fun didNewIncidentButtonPressed(){
        idle = if (idle){
            val filename = initNewIncidentFile()
            view?.showNewIncidentCounterScreen(filename)
            false
        } else {
            view?.dismissIncidentCounterScreen()
            initUIFromFileList()
            true
        }
    }

    private fun initUIFromFileList() {
        val files = ioFileStream.getFiles()
        Log.d("","")
        view?.showFileList(files)
    }

    private fun initNewIncidentFile(): String? {
        val date = Date().time
        return ioFileStream.createFileOnCard("incident_$date")
    }
}