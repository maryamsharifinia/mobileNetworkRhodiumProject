package com.example.myapplication

//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query

//@Dao
interface SignalDataDao {
//    @Insert
    suspend fun insert(signalData: SignalData)

//    @Query("SELECT * FROM signal_data")
    suspend fun getAllSignalData(): List<SignalData>
}
