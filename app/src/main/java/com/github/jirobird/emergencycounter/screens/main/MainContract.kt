package com.github.jirobird.emergencycounter.screens.main

import com.github.jirobird.mvp.MvpPresenter
import com.github.jirobird.mvp.MvpView

interface MainContract {
    interface IMainView: MvpView {
        fun showNewIncidentCounterScreen(filename:String?)
        fun dismissIncidentCounterScreen()
        fun disableUIToContinue()

        fun showFileList(files:List<String>?)
    }

    interface IMainPresenter:
        MvpPresenter<IMainView> {
        fun didPermissionGranted(granted:Boolean)
        fun didNewIncidentButtonPressed()
    }
}