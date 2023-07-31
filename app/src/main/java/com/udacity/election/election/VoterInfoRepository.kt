package com.udacity.election.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.election.database.SavedElectionDatabase
import com.udacity.election.database.VoterInfoDatabase
import com.udacity.election.network.CivicsInstance
import com.udacity.election.network.models.Election
import com.udacity.election.network.models.VoterInfo
import com.udacity.election.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VoterInfoRepository(
    private val voterInfoDatabase: VoterInfoDatabase,
    private val savedElectionDatabase: SavedElectionDatabase,
    private val civicInstance: CivicsInstance) {

    private val _voterInfo = MutableLiveData<VoterInfo>()
    val voterInfo: LiveData<VoterInfo>
        get() = _voterInfo

    suspend fun getElection(id: Int) : Election?{
        val election = withContext(Dispatchers.IO) {
            return@withContext savedElectionDatabase.get(id)
        }
        return election
    }
    suspend fun insertElection(election: Election) {
        withContext(Dispatchers.IO) {
            savedElectionDatabase.insert(election)
        }
    }
    suspend fun deleteElection(election: Election) {
        withContext(Dispatchers.IO) {
            savedElectionDatabase.delete(election)
        }
    }

    suspend fun refresh(address:String, id:Int) {
        withContext(Dispatchers.IO) {

            val response = civicInstance.getVoterInfo(address, id)
            val data = convert2VoterInfo(id, response)
            data?.run {
                voterInfoDatabase.insert(this)
            }
        }
    }

    private fun convert2VoterInfo(id: Int, response: VoterInfoResponse) : VoterInfo? {
        var voterInfo: VoterInfo? = null
        val state = response.state
        if (state?.isNotEmpty() == true) {
            val votingLocatinUrl: String? =
                state[0].electionAdministrationBody.votingLocationFinderUrl?.run {
                    this
                }
            val ballotInfoUrl: String? =
                state[0].electionAdministrationBody.ballotInfoUrl?.run {
                    this
                }
            voterInfo = VoterInfo(
                id,
                state[0].name,
                votingLocatinUrl,
                ballotInfoUrl
            )
        }

        return voterInfo
    }

    suspend fun loadById(id:Int) {
        withContext(Dispatchers.IO) {
            val data = voterInfoDatabase.get(id)
            data?.run {_voterInfo.postValue(this)}
        }
    }
}