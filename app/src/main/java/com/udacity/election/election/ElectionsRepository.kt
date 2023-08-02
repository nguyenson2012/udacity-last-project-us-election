package com.udacity.election.election

import android.util.Log
import com.udacity.election.database.ElectionDatabase
import com.udacity.election.database.SavedElectionDatabase
import com.udacity.election.network.CivicsInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository(
    private val electionDatabase: ElectionDatabase,
    savedElectionDatabase: SavedElectionDatabase,
    private val apiInstance: CivicsInstance) {

    val activeElections = electionDatabase.getAll()
    val savedElections = savedElectionDatabase.getAll()

    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val electionResponse = apiInstance.getElections()
            electionDatabase.insertAll(electionResponse.elections)
        }
    }
}