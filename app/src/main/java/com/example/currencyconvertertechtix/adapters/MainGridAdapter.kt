package com.example.currencyconvertertechtix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconvertertechtix.ConvertedCurrency
import com.example.currencyconvertertechtix.R

class RecyclerAdapter(private val list: List<ConvertedCurrency>) :
    RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.symbol)
        val value = view.findViewById<TextView>(R.id.result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_main_grid, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = list[position].symbol
        holder.value.text = list[position].result
    }

    override fun getItemCount(): Int {
        return list.count()
    }

}