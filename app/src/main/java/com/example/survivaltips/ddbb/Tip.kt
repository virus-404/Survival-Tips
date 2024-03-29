package com.example.survivaltips.ddbb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tip_table")
data class Tip(
    @PrimaryKey (autoGenerate = true)  var id: Long?,
    @ColumnInfo(name = "Title") var title: String,
    @ColumnInfo(name = "Image") var Image: String,
    @ColumnInfo(name = "Description") var description: String
)


// TODO https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#0