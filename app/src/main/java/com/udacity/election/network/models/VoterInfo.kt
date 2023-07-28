package com.udacity.election.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.election.database.Constants

@Entity(tableName = Constants.VOTER_INFO_TABLE_NAME)
data class VoterInfo(
    @PrimaryKey val id: Int,
    val stateName: String,
    val votingLocationUrl: String?,
    val ballotInformationUrl: String?)
