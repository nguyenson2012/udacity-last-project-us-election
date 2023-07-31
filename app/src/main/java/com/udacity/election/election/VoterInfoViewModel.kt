package com.udacity.election.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.election.R
import com.udacity.election.database.SavedElectionDatabase
import com.udacity.election.database.VoterInfoDatabase
import com.udacity.election.network.CivicsInstance
import com.udacity.election.network.models.Election
import com.udacity.election.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class VoterInfoViewModel(app: Application): BaseViewModel(app) {
    companion object {
        private const val DEFAULT_STATE = "ny"
    }

    private val voterInfoRepository = VoterInfoRepository(
        VoterInfoDatabase.getInstance(app),
        SavedElectionDatabase.getInstance(app),
        CivicsInstance
    )

    private val _selectedElection = MutableLiveData<Election>()
    val selectedElection : LiveData<Election>
        get() = _selectedElection

    val voterInfo = voterInfoRepository.voterInfo

    private val _isElectionSaved = MutableLiveData<Boolean?>()
    val isElectionSaved : LiveData<Boolean?>
        get() = _isElectionSaved

    init {
        _isElectionSaved.value = null
    }

    fun refresh(data: Election) {
        _selectedElection.value = data
        checkElectionSaved(data)
        refreshVoterInfo(data)
    }

    private fun checkElectionSaved(data: Election) {
        viewModelScope.launch {
            try {
                val savedElection = voterInfoRepository.getElection(data.id)
                _isElectionSaved.postValue(savedElection != null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun refreshVoterInfo(data: Election) {
        viewModelScope.launch {
            try {
                val state = data.division.state.ifEmpty { DEFAULT_STATE }
                val address = "${state},${data.division.country}"

                voterInfoRepository.refresh(address, data.id)
                voterInfoRepository.loadById(data.id)
            } catch (e: Exception) {
                e.printStackTrace()
                showSnackBarIntResource.postValue(R.string.no_network_message)
                voterInfoRepository.loadById(data.id)
            }
        }
    }

    fun onFollowButtonClick() {
        viewModelScope.launch {
            _selectedElection.value?.let {
                if(isElectionSaved.value == true) {
                    voterInfoRepository.deleteElection(it)
                } else {
                    voterInfoRepository.insertElection(it)
                }
                checkElectionSaved(it)
            }
        }
    }
}