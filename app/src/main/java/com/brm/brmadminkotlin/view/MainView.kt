package com.brm.brmadminkotlin.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.brm.brmadminkotlin.model.UsersModel

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun startLoading()
    fun endLoading()
    fun loadData(list: ArrayList<UsersModel>)
    fun loadEmptyList()
    fun showError(errorString: String)
    fun itemClick(position: Int)
}