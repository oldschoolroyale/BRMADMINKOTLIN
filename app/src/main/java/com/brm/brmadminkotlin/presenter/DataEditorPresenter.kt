package com.brm.brmadminkotlin.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.brm.brmadminkotlin.provider.DataEditorProvider
import com.brm.brmadminkotlin.view.DataEditorView

@InjectViewState
class DataEditorPresenter: MvpPresenter<DataEditorView>() {
    fun load(array: Array<String>){
        viewState.startLoading()
        DataEditorProvider(this).parse(array = array)
    }
    fun send(text: String){
        viewState.endLoading()
        viewState.goBack(text = text)
    }
    fun showError(error: String){
        viewState.endLoading()
        viewState.showError(error = error)
    }
}