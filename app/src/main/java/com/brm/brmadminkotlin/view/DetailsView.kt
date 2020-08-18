package com.brm.brmadminkotlin.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.brm.brmadminkotlin.model.StockModel

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface DetailsView : MvpView{
    fun startLoading()
    fun endLoading()
    fun showError(error:String)
    fun loadDetails(list: HashMap<String, String>)
    fun loadRecycler(list: ArrayList<StockModel>)
}