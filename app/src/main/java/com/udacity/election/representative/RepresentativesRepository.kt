package com.udacity.election.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.election.network.CivicsInstance
import com.udacity.election.representative.model.Representative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepresentativesRepository(private val instance: CivicsInstance) {

    private val _representatives = MutableLiveData<List<Representative>?>()
    val representatives: LiveData<List<Representative>?>
        get() = _representatives

    suspend fun refresh(addressStr: String) {
        withContext(Dispatchers.IO) {
            _representatives.postValue(null)
            val representativeResponse = CivicsInstance.getRepresentatives(addressStr)
            val representativeList = representativeResponse.offices.flatMap { office ->
                office.getRepresentatives(representativeResponse.officials)
            }

            _representatives.postValue(representativeList as MutableList<Representative>?)
        }
    }
}