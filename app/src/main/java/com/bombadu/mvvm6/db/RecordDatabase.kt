package com.bombadu.mvvm6.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Record::class], version = 1, exportSchema = false)
abstract class RecordDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao

    private class RecordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { recordDatabase ->
                scope.launch {
                    //populateDatabase(recordDatabase.recordDao())
                }
            }
        }


        /*fun populateDatabase(recordDao: RecordDao) {
            val thread = Thread {
                val myRecord = Record("4-26", 244, 10)
                recordDao.insertRecord(myRecord)
            }
            thread.start()

        }*/

    }


    companion object {
        @Volatile
        private var INSTANCE: RecordDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RecordDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecordDatabase::class.java,
                    "record_database"
                ).addCallback(RecordDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}
