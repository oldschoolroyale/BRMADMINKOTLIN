package com.brm.brmadminkotlin.provider

import android.util.Log
import com.brm.brmadminkotlin.presenter.DataEditorPresenter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DataEditorProvider(var presenter: DataEditorPresenter) {
    fun parse(array: Array<String>){
        val hashMap: HashMap<String, String> = HashMap()
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("DataBase").child(array[0]).child(array[1])
            .child(array[2]).child(array[3])
        hashMap["name"] = array[4]
        hashMap["address"] = array[5]
        reference.setValue(hashMap).addOnCompleteListener{
            if (it.isSuccessful){
                presenter.send("Успешно отправленно!")
            }
            else{
                presenter.showError("Ошибка в отправке!")
            }
        }
    }
    fun load(array: Array<String>){
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("DataBase").child(array[0])
            .child(array[1]).child(array[2]).child(array[3])
        Log.d("MyLog", array[0])
        Log.d("MyLog", array[1])
        Log.d("MyLog", array[2])
        Log.d("MyLog", array[3])
    }
}