package com.example.rickyandmorty.view

import android.os.Binder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.rickyandmorty.R
import com.example.rickyandmorty.databinding.FragmentDetailsBinding
import com.example.rickyandmorty.viewModel.DetailsViewModel
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private  lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        arguments?.let { bundle->
            val id=bundle.getInt("characterId")
            viewModel.getCharacterDetails(id)
            viewModel.characterDetails.observe(viewLifecycleOwner){
                binding.status.text=it.status
                binding.specy.text=it.species
                binding.gender.text=it.gender
                binding.location.text=it.location.name
                binding.episodes.text=""
                for (items in it.episode){
                    val episodesFilter=items.filter { it.isDigit() }
                    binding.episodes.append("${episodesFilter},")
                }
                binding.createdAt.text=it.created
                Picasso.get().load(it.image).into(binding.characterImage)
                binding.characteraName.text=it.name
            }
        }
        binding.backPage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}