package com.brm.brmadminkotlin.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.brm.brmadminkotlin.model.StockModel
import com.brm.brmadminkotlin.provider.DetailsProvider
import com.brm.brmadminkotlin.view.DetailsView

@InjectViewState
class DetailsPresenter: MvpPresenter<DetailsView>() {
    fun load(token: String, id: String, year: String, month: String, day: String){
        viewState.startLoading()
        DetailsProvider(this).parse(token, id, year, month, day)
    }
    fun tryToLoad(list: HashMap<String, String>, token: String, id: String, year: String, month: String, day: String){
        viewState.loadDetails(list = list)
        DetailsProvider(this).parseStock(token, id, year, month, day)
    }
    fun loadRecycler(list: ArrayList<StockModel>){
        viewState.endLoading()
        viewState.loadRecycler(list = list)
    }
    fun showError(error: String){
        viewState.endLoading()
        viewState.showError(error = error)
    }
}