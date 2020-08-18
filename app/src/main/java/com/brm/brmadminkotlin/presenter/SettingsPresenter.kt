package com.brm.brmadminkotlin.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.brm.brmadminkotlin.provider.SettingsProvider
import com.brm.brmadminkotlin.view.SettingsView

@InjectViewState
class SettingsPresenter: MvpPresenter<SettingsView>() {

    fun sendData(data: String, token: String, key: String){
        viewState.startLoading()
        if (key == "manager"){SettingsProvider(this).managerParse(data, token, key)}
        else{SettingsProvider(this).loadData(data = data, token = token, key = key)}
    }

    fun load(data: String, token: String, key: String){
        SettingsProvider(this).loadData(data, token, key)
    }

    fun loadData(town: String){
        viewState.startLoading()
        SettingsProvider(this).loadRegion(town = town)
    }

    fun loadMultiSelection(list: Array<String>){
        viewState.endLoading()
        viewState.multiSelectionAlert(key = "region", list = list)
    }

    fun showMessage(errorString: String){
        viewState.endLoading()
        viewState.errorToast(errorString = errorString)
    }
}