package com.udacity.election.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.election.network.models.VoterInfo

@Dao
interface VoterInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(voterInfo: VoterInfo)

    @Query("SELECT * FROM ${Constants.VOTER_INFO_TABLE_NAME} WHERE id = :id")
    suspend fun get(id: Int) : VoterInfo?
}