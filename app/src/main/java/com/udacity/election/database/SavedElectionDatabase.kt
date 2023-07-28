package com.udacity.election.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.election.network.models.Election

@Database(entities = [Election::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SavedElectionDatabase: RoomDatabase() {
    protected abstract val dao: ElectionDao

    companion object {
        @Volatile
        private var INSTANCE: SavedElectionDatabase? = null

        fun getInstance(context: Context): SavedElectionDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SavedElectionDatabase::class.java,
                        "saved_election_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    fun getAll() = dao.getAll()
    suspend fun get(id: Int) = dao.getById(id)
    suspend fun insert(election: Election) = dao.insertOne(election)
    suspend fun delete(election: Election) = dao.deleteOne(election)
}