package com.udacity.election.launch

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.udacity.election.databinding.FragmentLaunchBinding

class LaunchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentLaunchBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.btnRepresentatives.setOnClickListener { navToRepresentatives() }
        binding.btnUpcomingElection.setOnClickListener { navToElections() }

        return binding.root
    }

    private fun navToElections() {
        this.findNavController().navigate(LaunchFragmentDirections.actionLaunchFragmentToElectionFragment())
    }

    private fun navToRepresentatives() {
        this.findNavController().navigate(LaunchFragmentDirections.actionLaunchFragmentToRepresentativeFragment())
    }

}
