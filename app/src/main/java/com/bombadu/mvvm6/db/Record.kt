package com.bombadu.mvvm6.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_table")
data class Record (
    @ColumnInfo(name = "date") var date: String,

    @ColumnInfo(name = "weight") var weight: Int,

    @ColumnInfo(name = "mood") var mood: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}