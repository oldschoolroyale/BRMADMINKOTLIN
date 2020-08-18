package com.brm.brmadminkotlin.provider

import android.os.Handler
import android.util.Log
import com.brm.brmadminkotlin.presenter.SettingsPresenter
import com.google.firebase.database.*

class SettingsProvider(var presenter: SettingsPresenter) {

    fun loadData(data: String, token: String, key: String){
            val reference : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Account").child(token)
            reference.child(key).setValue(data).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    presenter.showMessage("Успешно отправленно!")
                    Log.d("MyLog", "$data/$token/$key")
                }
                else{
                    presenter.showMessage("Ошибка!")
                }
            }

    }

    fun loadRegion(town: String){
        Handler().postDelayed({
            val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Uzbekistan").child(town)
            reference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    presenter.showMessage(errorString = error.toString())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataArray: ArrayList<String> = ArrayList()
                    for (i in snapshot.children){
                        dataArray.add(i.child("name").value.toString())
                    }
                    presenter.loadMultiSelection(dataArray.toTypedArray())
                }
            })
        }, 1500)
    }

    fun managerParse(name: String, token: String, key: String){
        if (name == "Ботиров Жамшид") {
            presenter.load("vMjkEZ1FIBRhPcOYQUQ1wj4yzE92", token, key)
        }
        if (name == "Юлдуз Турсунова") {
            presenter.load("laSLchSYIhcudpiI18tzWBw3Fqi1", token, key)
        }
        if (name == "Каршиев Рустам") {
            presenter.load("v81LfKtNlbTKEagT3SNOQfGAWl92", token, key)
        }
        if (name == "Таваккал Акрамов") {
            presenter.load("nblpWcEXHTQruG2QzyqdEwFtcF73", token, key)
        }
        if (name == "Холмуминов Маруф") {
            presenter.load("7TjTNxx8mHhXfspMCjQvhppPASz1", token, key)
        }
        if (name == "Асадуллаев Шухратилло") {
            presenter.load("kX7OCKH4MCPnFbZjuvTC88lVax32", token, key)
        }
        if (name == "Карриев Кахрамон") {
            presenter.load("eyixSdjYR3gkUxVGemXtjOPiPrl1", token, key)
        }
        if (name == "Рахимов Голиб") {
            presenter.load("IFO0g6qO5JgazGulq0jF6Xat0Dj1", token, key)
        }
        if (name == "Байкунусова Шахноза") {
            presenter.load("Tha9VZ6forW2hrutOYpXAnjGIr13", token, key)
        }
        if (name == "Без менеджера") {
            presenter.load("null", token, key)
        }
    }
}