package com.brm.brmadminkotlin.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.brm.brmadminkotlin.model.NotesModel

@StateStrategyType(AddToEndSingleStrategy::class)
interface NotesView : MvpView {
    fun startLoading()
    fun endLoading()
    fun showError(error: String)
    fun loadEmptyList()
    fun loadList(list: ArrayList<NotesModel>)
    fun itemClick(position: Int)
}