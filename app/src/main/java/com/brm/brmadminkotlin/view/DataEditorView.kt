package com.brm.brmadminkotlin.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface DataEditorView: MvpView{
    fun startLoading()
    fun endLoading()
    fun showError(error: String)
    fun goBack(text: String)
}