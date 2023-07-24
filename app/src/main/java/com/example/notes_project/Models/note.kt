package com.example.notes_project.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class note(
    @PrimaryKey (autoGenerate = true) val id:Int?,
    @ColumnInfo(name = "title") val Title:String?,
    @ColumnInfo(name = "note")val note:String?,
    @ColumnInfo(name = "date")val date:String?
): java.io.Serializable
