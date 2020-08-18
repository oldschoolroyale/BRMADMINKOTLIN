package com.brm.brmadminkotlin.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.brm.brmadminkotlin.provider.LoginProvider
import com.brm.brmadminkotlin.view.LoginView

@InjectViewState
class LoginPresenter: MvpPresenter<LoginView>() {
    fun load(email: String, password: String){
        viewState.startLoading()
        LoginProvider(this).parseUser(email, password)
    }
    fun tryToSetUp(check: Boolean){
        viewState.endLoading()
        if (check){
            viewState.sendHome()
        }
        else{
            viewState.showError("Что-то пошло не так. Попробуйте еще раз!")
        }
    }
}