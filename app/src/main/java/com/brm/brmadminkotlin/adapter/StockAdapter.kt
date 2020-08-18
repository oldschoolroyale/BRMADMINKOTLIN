package com.brm.brmadminkotlin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brm.brmadminkotlin.R
import com.brm.brmadminkotlin.model.StockModel

class StockAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var stockList: ArrayList<StockModel> = ArrayList()

    fun setUpStockList(stockNewList: ArrayList<StockModel>){
       stockList.clear()
       stockList.addAll(stockNewList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.stock_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return stockList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StockViewHolder){
            holder.bind(stockList[position])
        }
    }
    private class StockViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var nameText = view.findViewById(R.id.stock_list_item_name_text) as TextView
        var quantityText = view.findViewById(R.id.stock_list_item_address) as TextView

        @SuppressLint("SetTextI18n")
        fun bind(holder: StockModel){
            nameText.text = holder.name
            quantityText.text = "Количество: ${holder.quantity}"
        }
    }
}