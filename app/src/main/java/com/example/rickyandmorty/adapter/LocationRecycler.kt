package com.example.rickyandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickyandmorty.R
import com.example.rickyandmorty.model.Result
import com.example.rickyandmorty.util.constant
import com.example.rickyandmorty.viewModel.MaiinViewModel

class LocationRecycler (val viewModel:MaiinViewModel):RecyclerView.Adapter<LocationRecycler.LocationVH>() {

    val differ = object :DiffUtil.ItemCallback<Result>(){

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return newItem==oldItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return newItem==oldItem
        }
    }
    val asyncItems =AsyncListDiffer(this,differ)

    var locationItems:List<Result>
    get() =asyncItems.currentList
        set(value)=asyncItems.submitList(value)

    class LocationVH (itemview:View):RecyclerView.ViewHolder(itemview){
        val locationName=itemview.findViewById<TextView>(R.id.locationName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationVH {
        val vieww =LayoutInflater.from(parent.context).inflate(R.layout.location_recycler_desing,parent,false)
        return LocationVH(vieww)
    }

    override fun onBindViewHolder(holder: LocationVH, position: Int) {
        holder.locationName.text=locationItems[position].name
        if (position==constant.locationSelected){
            holder.locationName.setBackgroundResource(R.drawable.selected_location_shape)
        }else{
            holder.locationName.setBackgroundResource(R.drawable.location_shape)
        }
        holder.locationName.setOnClickListener {
            constant.locationSelected=position
            getCharacter(locationItems[position].residents)
            notifyDataSetChanged()
        }
    }
    override fun getItemCount(): Int {
        return locationItems.size
    }

    fun getCharacter(residents:List<String>){
        val characterList=ArrayList<Int>()
        for (characters in residents){
            val characterIndex=characters.filter { it.isDigit() }
            characterList.add(characterIndex.toInt())
        }
        viewModel.getCharacterData(characterList)
    }
}