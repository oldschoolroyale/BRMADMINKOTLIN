package com.brm.brmadminkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brm.brmadminkotlin.R
import com.brm.brmadminkotlin.model.UsersModel
import com.brm.brmadminkotlin.view.MainView
import com.brm.brmadminkotlin.viewHolder.UsersViewHolder

class UsersAdapter(var mainView: MainView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var usersList : ArrayList<UsersModel> = ArrayList()


    fun setUpUserList(userNewList: ArrayList<UsersModel>){
        usersList.clear()
        usersList.addAll(userNewList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.users_list_item, parent, false)
        return UsersViewHolder(view = view)
    }

    override fun getItemCount(): Int {
       return usersList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UsersViewHolder){
            holder.bind(usersList[position], mainView = mainView)
        }
    }

}