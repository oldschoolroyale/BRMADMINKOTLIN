package com.brm.brmadminkotlin.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.brm.brmadminkotlin.model.DataBaseModel
import com.brm.brmadminkotlin.provider.DataBaseProvider
import com.brm.brmadminkotlin.view.DataBaseView

@InjectViewState
class DataBasePresenter : MvpPresenter<DataBaseView>() {
    fun startLoading(type: String, town: String, region: String){
        viewState.startLoading()
        DataBaseProvider(this).parse(type = type, town = town, region = region)
    }
    fun endLoading(list: ArrayList<DataBaseModel>){
        viewState.endLoading()
        if (list.isEmpty()){
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
}