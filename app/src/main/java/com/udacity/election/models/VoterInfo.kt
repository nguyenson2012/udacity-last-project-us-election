package com.udacity.election.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConstants.VOTER_INFO_TABLE_NAME)
data class VoterInfo(
    @PrimaryKey val id: Int,
    val stateName: String,
    val votingLocationUrl: String?,
    val ballotInformationUrl: String?)
