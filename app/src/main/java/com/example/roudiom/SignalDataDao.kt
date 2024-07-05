package com.example.roudiom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SignalDataDao {

    @Insert
    suspend fun insert(signalData: SignalData): Long

    @Query("SELECT * FROM signal_data")
    fun getAllSignalData(): List<SignalData>
}
