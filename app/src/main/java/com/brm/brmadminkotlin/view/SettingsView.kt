package com.brm.brmadminkotlin.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface SettingsView : MvpView{
    fun startLoading()
    fun endLoading()
    fun errorToast(errorString: String)
    fun multiSelectionAlert(key: String, list: Array<String>)
    fun singleSelectionAlert(key: String, list: Array<String>)
}