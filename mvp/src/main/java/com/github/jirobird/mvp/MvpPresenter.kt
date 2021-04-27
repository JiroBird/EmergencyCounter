package com.github.jirobird.mvp

interface MvpPresenter <V: MvpView> {
    fun attachView(mvpView: V)
    fun deattachView()
    fun viewIsReady()
    fun destroy()
}