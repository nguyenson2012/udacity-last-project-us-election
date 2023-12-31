package com.udacity.election.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.election.network.models.VoterInfo

@Database(entities = [VoterInfo::class], version = 1, exportSchema = false)
abstract class VoterInfoDatabase: RoomDatabase() {

    abstract val voterInfoDao: VoterInfoDao

    companion object {
        @Volatile
        private var INSTANCE: VoterInfoDatabase? = null

        fun getInstance(context: Context): VoterInfoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VoterInfoDatabase::class.java,
                        "voter_info_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    suspend fun insert(voterInfo: VoterInfo) = voterInfoDao.insert(voterInfo)
    suspend fun get(id: Int) = voterInfoDao.get(id)
}