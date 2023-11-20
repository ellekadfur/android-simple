package com.example.countries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.model.Country
import android.widget.TextView
import com.example.countries.R

class CountryAdapter(val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName: TextView = itemView.findViewById(R.id.tv_name)
        val textCode: TextView = itemView.findViewById(R.id.tv_code)
        val textCapital: TextView = itemView.findViewById(R.id.tv_capital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        //Here set Value from list
        val country = countryList[position]
        holder.textName.text = "${country.name}, ${country.region}"
        holder.textCode.text = "${country.code}"
        holder.textCapital.text = "${country.capital}"

    }

    override fun getItemCount(): Int = countryList.size

    fun updateList(myList : List<Country>) {
        countryList.addAll(myList)
        notifyDataSetChanged()

    }

}