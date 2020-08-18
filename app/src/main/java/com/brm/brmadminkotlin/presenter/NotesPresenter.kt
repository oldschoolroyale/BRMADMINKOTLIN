package com.brm.brmadminkotlin.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.brm.brmadminkotlin.model.NotesModel
import com.brm.brmadminkotlin.provider.NotesProvider
import com.brm.brmadminkotlin.view.NotesView

@InjectViewState
class NotesPresenter: MvpPresenter<NotesView>() {
    fun loadData(token: String, year: String, month: String, day: String){
        viewState.startLoading()
        NotesProvider(this).parse(token = token, year = year, month = month, day = day)
    }

    fun tryToSetUp(list: ArrayList<NotesModel>){
        viewState.endLoading()
        if (list.size == 0){
            viewState.loadEmptyList()
        }
        else{
            viewState.loadList(list = list)
        }
    }

    fun showError(error: String){
        viewState.endLoading()
        viewState.showError(error = error)
    }

    fun setName(name: String, token: String){
        viewState.startLoading()
        NotesProvider(this).sendName(name = name, token = token)
    }
}