package com.udacity.election.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.election.R
import com.udacity.election.database.ElectionDatabase
import com.udacity.election.database.SavedElectionDatabase
import com.udacity.election.network.CivicsInstance
import com.udacity.election.network.models.Election
import com.udacity.election.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class ElectionsViewModel(app: Application): BaseViewModel(app) {
    private val electionsRepository = ElectionsRepository(
        ElectionDatabase.getInstance(app),
        SavedElectionDatabase.getInstance(app),
        CivicsInstance
    )
    val activeElections = electionsRepository.activeElections
    val savedElections = electionsRepository.savedElections

    init {
        refreshElections()
    }

    private fun refreshElections() {
        viewModelScope.launch {
            try {
                electionsRepository.refresh()
            } catch (e: Exception) {
                e.printStackTrace()
                showSnackBarIntResource.postValue(R.string.no_network_message)
            }
        }
    }

}