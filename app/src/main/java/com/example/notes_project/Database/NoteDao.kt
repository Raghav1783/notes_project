package com.example.notes_project.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notes_project.Models.note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: note)

    @Delete
    suspend fun delete(note: note)

    @Query("Select * from note_table order by id ASC")
    fun getAllNotes() : LiveData<List<note>>

    @Query("UPDATE note_table Set title = :title ,note = :note WHERE id= :id")
    suspend fun update(id: Int?, title:String?,note:String?)


}