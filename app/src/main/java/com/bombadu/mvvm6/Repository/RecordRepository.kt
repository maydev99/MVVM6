package com.bombadu.mvvm6.Repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.bombadu.mvvm6.db.Record
import com.bombadu.mvvm6.db.RecordDao

class RecordRepository(private val recordDao: RecordDao) {

    val allRecords: LiveData<List<Record>> = recordDao.getALLRecords()

    fun insertRecord(record: Record) {
        InsertRecordAsyncTask(
            recordDao
        ).execute(record)

    }

    fun deleteAllRecords(){
        DeleteAllRecordsAsyncTask(
            recordDao
        ).execute()

    }

    fun deleteRecords(record: Record){
        DeleteRecordAsyncTask(
            recordDao
        ).execute(record)
    }

    fun updateRecords(record: Record){
        UpdateRecordAsyncTask(
            recordDao
        ).execute(record)

    }

    private class  InsertRecordAsyncTask(val recordDao: RecordDao) : AsyncTask<Record, Unit, Unit>(){
        override fun doInBackground(vararg record: Record?) {
            recordDao.insertRecord(record[0]!!)
        }

    }

    private class  DeleteAllRecordsAsyncTask(val recordDao: RecordDao) : AsyncTask<Unit, Unit, Unit>(){
        override fun doInBackground(vararg p0: Unit) {
            recordDao.delteAllRecords()
        }

    }


    private class  DeleteRecordAsyncTask(val recordDao: RecordDao) : AsyncTask<Record, Unit, Unit>(){
        override fun doInBackground(vararg record: Record?) {
            recordDao.deleteRecord(record[0]!!)
        }

    }


    private class  UpdateRecordAsyncTask(val recordDao: RecordDao) : AsyncTask<Record, Unit, Unit>(){
        override fun doInBackground(vararg record: Record?) {
            recordDao.updateRecord(record[0]!!)
        }

    }



}