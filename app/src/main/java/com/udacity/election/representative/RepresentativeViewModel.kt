package com.udacity.election.representative

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.election.R
import com.udacity.election.network.CivicsInstance
import com.udacity.election.network.models.Address
import com.udacity.election.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class RepresentativesViewModel(app: Application): BaseViewModel(app) {
    private val representativesRepository = RepresentativesRepository(CivicsInstance)

    val representatives = representativesRepository.representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    private val _states = MutableLiveData<List<String>>()
    val states: LiveData<List<String>>
        get() = _states

    private val selectedIndex = MutableLiveData<Int>()

    init {
        _address.value = Address("", "","","New York","")
        _states.value = app.resources.getStringArray(R.array.states).toList()
    }

    fun clickSearchButton() {
        refreshRepresentatives()
    }

    private fun refreshRepresentatives() {
        viewModelScope.launch {
            try {
                address.value!!.state = getSelectedState(selectedIndex.value!!)
                val addressStr = address.value!!.toFormattedString()
                representativesRepository.refresh(addressStr)

            } catch (e: Exception) {
                e.printStackTrace()
                showSnackBarInt.postValue(R.string.no_network_message)
            }
        }
    }

    private fun getSelectedState(stateIndex: Int) : String {
        return states.value!!.toList()[stateIndex]
    }

    fun refreshAddress(address: Address) {
        val stateIndex = _states.value?.indexOf(address.state)
        if (stateIndex != null && stateIndex >= 0) {
            selectedIndex.value = stateIndex!!
            _address.value = address
            refreshRepresentatives()
        }
    }
}
