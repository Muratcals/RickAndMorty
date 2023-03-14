package com.example.rickyandmorty.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickyandmorty.R
import com.example.rickyandmorty.model.CharacterModel
import com.example.rickyandmorty.model.Result
import com.squareup.picasso.Picasso

class CharacterRecycler(val view: View): RecyclerView.Adapter<CharacterRecycler.PeopleVH>() {

    val rightPosition=1
    val leftPosition=2
    val differ = object : DiffUtil.ItemCallback<CharacterModel>(){

        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return newItem==oldItem
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return newItem==oldItem
        }
    }

    val asyncItems = AsyncListDiffer(this,differ)

    var characterItems:List<CharacterModel>
        get() =asyncItems.currentList
        set(value)=asyncItems.submitList(value)

    class PeopleVH (itemview: View):RecyclerView.ViewHolder(itemview){
        val charactersName =itemview.findViewById<TextView>(R.id.characterName)
        val charactersImage =itemview.findViewById<ImageView>(R.id.characterImage)
        val genderImage =itemview.findViewById<ImageView>(R.id.genderImage)
        val characterLinear=itemview.findViewById<ConstraintLayout>(R.id.characterLinear)
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleVH {
        val vieww:View
        if (viewType==rightPosition){
            vieww =LayoutInflater.from(parent.context).inflate(R.layout.character_right_design,parent,false)
        }else{
            vieww =LayoutInflater.from(parent.context).inflate(R.layout.character_recycler_desing,parent,false)
        }
        return PeopleVH(vieww)
    }
    override fun onBindViewHolder(holder: PeopleVH, position: Int) {
        holder.charactersName.text=characterItems[position].name
        Picasso.get().load(characterItems[position].image).into(holder.charactersImage)
        if (characterItems[position].gender.equals("Female")){
            holder.genderImage.setImageResource(R.drawable.female)
        }else if (characterItems[position].gender.equals("Male")){
            holder.genderImage.setImageResource(R.drawable.male)
        }else if (characterItems[position].gender.equals("unknown")){
            holder.genderImage.setImageResource(R.drawable.female_male)
        }
        holder.characterLinear.setOnClickListener {
            val arguments= bundleOf("characterId" to characterItems[position].id)
            view.findNavController().navigate(R.id.action_maiinFragment_to_detailsFragment,arguments)
        }
    }

    override fun getItemCount(): Int {
       return characterItems.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position%2==0){
            return leftPosition
        }else{
            return rightPosition
        }
    }
}