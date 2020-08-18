package com.brm.brmadminkotlin.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brm.brmadminkotlin.R
import com.brm.brmadminkotlin.model.UsersModel
import com.brm.brmadminkotlin.view.MainView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UsersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
     private var mainTextView = view.findViewById(R.id.users_list_item_name) as TextView
     private var stateTextView= view.findViewById(R.id.users_list_item_state) as TextView

     private var versionTextView= view.findViewById(R.id.users_list_item_version) as TextView
     private var userImageView= view.findViewById(R.id.user_list_item_circle_image) as CircleImageView

    fun bind(usersModel: UsersModel, mainView: MainView){
        usersModel.image?.let { url -> Picasso.with(itemView.context).load(url).placeholder(R.drawable.ic_baseline_person_outline_24).into(userImageView)  }
        mainTextView.text = usersModel.name
        stateTextView.text = usersModel.town
        versionTextView.text = usersModel.version
        itemView.setOnClickListener(View.OnClickListener {
            mainView.itemClick(adapterPosition)
        })
    }
}