package com.bombadu.mvvm6.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bombadu.mvvm6.Repository.RecordRepository
import com.bombadu.mvvm6.db.Record
import com.bombadu.mvvm6.db.RecordDatabase

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecordRepository
    private var allRecords: LiveData<List<Record>>

    init {
        val recordDao = RecordDatabase.getDatabase(application, viewModelScope).recordDao()
        repository = RecordRepository(recordDao)
        allRecords = repository.allRecords
    }

    fun insertRecord(record: Record) {
        repository.insertRecord(record)
    }

    fun deleteAllRecords() {
        repository.deleteAllRecords()
    }

    fun deleteRecord(record: Record) {
        repository.deleteRecords(record)
    }

    fun updateRecord(record: Record) {
        repository.updateRecords(record)

    }

    fun getAllRecords(): LiveData<List<Record>> {
        return allRecords
    }
}