package com.brm.brmadminkotlin.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.brm.brmadminkotlin.model.UsersModel
import com.brm.brmadminkotlin.provider.MainProvider
import com.brm.brmadminkotlin.view.MainView

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    fun checkManager(manager: String){
        MainProvider(this).managerParse(manager)
    }
    fun load(manager: String){
        viewState.startLoading()
        MainProvider(this).parse(manager)
    }
    fun trySetUp(userList: ArrayList<UsersModel>){
        viewState.endLoading()
        if (userList.size == 0){
            viewState.loadEmptyList()
        }
        else{
            viewState.loadData(userList)
        }
    }
    fun showError(errorString: String){
        viewState.endLoading()
        viewState.showError(errorString)
    }
}