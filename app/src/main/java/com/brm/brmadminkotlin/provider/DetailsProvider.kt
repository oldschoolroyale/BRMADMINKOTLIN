package com.brm.brmadminkotlin.provider

import com.brm.brmadminkotlin.model.StockModel
import com.brm.brmadminkotlin.presenter.DetailsPresenter
import com.google.firebase.database.*

class DetailsProvider(var presenter: DetailsPresenter) {
    fun parse(token: String, id: String, year: String, month: String, day: String){
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Notes")
            .child(token).child(year).child(month).child(day).child(id)
        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                presenter.showError(error = error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                val map : HashMap<String, String> = hashMapOf(
                    "name" to snapshot.child("name").value.toString(),
                    "address" to snapshot.child("address").value.toString(),
                    "time_start" to snapshot.child("time_start").value.toString(),
                    "time_end" to snapshot.child("time_end").value.toString(),
                    "medications" to snapshot.child("medications").value.toString(),
                    "comment" to snapshot.child("comment").value.toString(),
                    "lat" to snapshot.child("lat").value.toString(),
                    "lon" to snapshot.child("lon").value.toString()
                )
                presenter.tryToLoad(map, token, id, year, month, day)
            }

        })
    }
    fun parseStock(token: String, id: String, year: String, month: String, day: String){
        val parseList: ArrayList<StockModel> = ArrayList()
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Notes")
            .child(token).child(year).child(month).child(day).child(id).child("stock")
        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                presenter.showError(error = error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children){
                    val model = i.getValue(StockModel::class.java)
                    parseList.add(model!!)
                }
                presenter.loadRecycler(parseList)

            }

        })
    }
}