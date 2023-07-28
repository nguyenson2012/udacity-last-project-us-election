package com.udacity.election.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.election.network.models.Election

@Dao
interface ElectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(elections: List<Election>)

    @Query("SELECT * FROM ${Constants.ELECTION_TABLE_NAME}")
    fun getAll(): LiveData<List<Election>>

    @Query("SELECT * FROM ${Constants.ELECTION_TABLE_NAME} WHERE id = :id")
    suspend fun getById(id: Int) : Election?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(election: Election)

    @Delete
    suspend fun deleteOne(election: Election)

    @Query("DELETE FROM ${Constants.ELECTION_TABLE_NAME}")
    suspend fun clear()
}