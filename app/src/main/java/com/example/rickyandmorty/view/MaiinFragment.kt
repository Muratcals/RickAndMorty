package com.example.rickyandmorty.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickyandmorty.adapter.LocationRecycler
import com.example.rickyandmorty.adapter.CharacterRecycler
import com.example.rickyandmorty.databinding.FragmentMaiinBinding
import com.example.rickyandmorty.viewModel.MaiinViewModel

class MaiinFragment : Fragment() {

    private lateinit var binding:FragmentMaiinBinding
    private lateinit var viewModel: MaiinViewModel
    private lateinit var locationAdapter:LocationRecycler
    private lateinit var characterAdapter:CharacterRecycler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentMaiinBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = MaiinViewModel(view)
        viewModel.getLocationData()
        observerItem()
        locationAdapter=LocationRecycler(viewModel)
        characterAdapter=CharacterRecycler(view)
        binding.characterRecycler.adapter=characterAdapter
        binding.characterRecycler.layoutManager=LinearLayoutManager(requireContext())
        binding.locationRecycler.adapter=locationAdapter
        binding.locationRecycler.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        viewModel.locationResult.observe(viewLifecycleOwner){
            locationAdapter.locationItems=it
            locationAdapter.notifyDataSetChanged()
        }
        viewModel.characterResult.observe(viewLifecycleOwner){
            characterAdapter.characterItems=it
            characterAdapter.notifyDataSetChanged()
        }

    }
    fun observerItem(){
        viewModel.emptyItems.observe(viewLifecycleOwner){
            if (it){
                binding.emptyItems.visibility=View.VISIBLE
                binding.characterRecycler.visibility=View.INVISIBLE
            }else{
                binding.emptyItems.visibility=View.INVISIBLE
                binding.characterRecycler.visibility=View.VISIBLE
            }
        }
    }
}