package com.example.dnd5emonsterviewer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dnd5emonsterviewer.MainViewModel
import com.example.dnd5emonsterviewer.databinding.FragmentHomeBinding
import com.example.dnd5emonsterviewer.adapter.MonsterAdapter

class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val monsterAdapter = MonsterAdapter()
        binding.monsterRecycler.adapter = monsterAdapter

        viewModel.monsters.observe(viewLifecycleOwner) {
            monsterAdapter.submitList(it)
        }

        binding.filterButton.setOnClickListener {
            viewModel.filter(binding.filterEditText.text.toString().lowercase(), requireContext())
        }

        binding.sortButton.setOnClickListener {
            viewModel.sortBy(binding.sortbySpinner.selectedItem.toString(), requireContext())
        }
    }
}