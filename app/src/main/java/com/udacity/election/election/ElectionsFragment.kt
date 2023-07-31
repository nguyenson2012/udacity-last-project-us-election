package com.udacity.election.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import com.udacity.election.BaseFragment
import com.udacity.election.databinding.FragmentElectionBinding
import com.udacity.election.election.adapter.ElectionItemClickListener
import com.udacity.election.election.adapter.ElectionListAdapter

class ElectionsFragment: BaseFragment() {

    private val navController by lazy { findNavController() }
    override val viewModel: ElectionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {
        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val activeElectionAdapter = ElectionListAdapter(ElectionItemClickListener { election ->
            navController.navigate(
                ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election)
            )
        })

        binding.recyclerviewUpcomingElections.adapter = activeElectionAdapter
        viewModel.activeElections.observe(viewLifecycleOwner) { elections ->
            activeElectionAdapter.submitList(elections)
        }

        val savedElectionAdapter = ElectionListAdapter(ElectionItemClickListener { election ->
            navController.navigate(
                ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election))
        })

        binding.recyclerviewSavedElection.adapter = savedElectionAdapter
        viewModel.savedElections.observe(viewLifecycleOwner) { elections ->
            savedElectionAdapter.submitList(elections)
        }
        return binding.root
    }
}