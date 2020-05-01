package com.bombadu.mvvm6.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecordDao {

    @Insert
    fun insertRecord(record: Record)

    @Update
    fun updateRecord(record: Record)

    @Delete
    fun deleteRecord(record: Record)

    @Query("DELETE FROM record_table")
    fun delteAllRecords()

    @Query("SELECT * FROM record_table ORDER BY date DESC")
    fun getALLRecords(): LiveData<List<Record>>
}