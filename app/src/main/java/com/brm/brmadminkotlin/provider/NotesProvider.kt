package com.brm.brmadminkotlin.provider

import com.brm.brmadminkotlin.model.NotesModel
import com.brm.brmadminkotlin.presenter.NotesPresenter
import com.google.firebase.database.*

class NotesProvider(val presenter: NotesPresenter) {
    fun parse(token: String, year: String, month: String, day: String){
            val loadList: ArrayList<NotesModel> = ArrayList()
            val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Notes")
                .child(token).child(year).child(month).child(day)
            reference.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    presenter.showError(error = error.toString())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    loadList.clear()
                    for (i in snapshot.children){
                        val note = i.getValue(NotesModel::class.java)
                        loadList.add(note!!)
                    }
                    presenter.tryToSetUp(loadList)
                }
            })
    }
    fun sendName(name: String, token: String){
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Account").child(token)
        reference.child("name").setValue(name)
        presenter.showError("Имя измененно!")
    }
}