package com.brm.brmadminkotlin.provider

import com.brm.brmadminkotlin.model.UsersModel
import com.brm.brmadminkotlin.presenter.MainPresenter
import com.google.firebase.database.*

class MainProvider (var presenter : MainPresenter) {
    fun managerParse(name: String){
        if (name == "Ботиров Жамшид") {
            presenter.load("vMjkEZ1FIBRhPcOYQUQ1wj4yzE92")
        }
        if (name == "Юлдуз Турсунова") {
            presenter.load("laSLchSYIhcudpiI18tzWBw3Fqi1")
        }
        if (name == "Каршиев Рустам") {
            presenter.load("v81LfKtNlbTKEagT3SNOQfGAWl92")
        }
        if (name == "Таваккал Акрамов") {
            presenter.load("nblpWcEXHTQruG2QzyqdEwFtcF73")
        }
        if (name == "Холмуминов Маруф") {
            presenter.load("7TjTNxx8mHhXfspMCjQvhppPASz1")
        }
        if (name == "Асадуллаев Шухратилло") {
            presenter.load("kX7OCKH4MCPnFbZjuvTC88lVax32")
        }
        if (name == "Карриев Кахрамон") {
            presenter.load("eyixSdjYR3gkUxVGemXtjOPiPrl1")
        }
        if (name == "Рахимов Голиб") {
            presenter.load("IFO0g6qO5JgazGulq0jF6Xat0Dj1")
        }
        if (name == "Байкунусова Шахноза") {
            presenter.load("Tha9VZ6forW2hrutOYpXAnjGIr13")
        }
        if (name == "Без менеджера") {
            presenter.load("null")
        }
    }
    fun parse(token: String){
            val loadList: ArrayList<UsersModel> = ArrayList()

            val databaseReference = FirebaseDatabase.getInstance().reference.child("Account")
            val query : Query = databaseReference.orderByChild("manager").startAt(token).endAt(token + "\uf8ff")
            query.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    presenter.showError(errorString = error.toString())
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    loadList.clear()
                    for(i in snapshot.children){
                        val card = i.getValue(UsersModel::class.java)
                        loadList.add(card!!)
                    }
                    presenter.trySetUp(loadList)
                }

            })
    }
}