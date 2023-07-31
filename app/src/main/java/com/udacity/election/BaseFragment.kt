package com.udacity.election

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.udacity.election.viewmodel.BaseViewModel

abstract class BaseFragment : Fragment() {
    protected abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showSnackBarStrResource.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        }

        viewModel.showSnackBarIntResource.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), getString(it), Snackbar.LENGTH_LONG).show()
        }
    }
}