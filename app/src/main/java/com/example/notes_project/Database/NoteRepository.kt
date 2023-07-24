package com.example.notes_project.Database

import androidx.lifecycle.LiveData
import com.example.notes_project.Models.note

class NoteRepository(private val noteDao: NoteDao) {
    val allnotes : LiveData<List<note>> = noteDao.getAllNotes()

    suspend fun insert(note:note){
        noteDao.insert(note)
    }

    suspend fun delete(note:note){
        noteDao.delete(note)

    }
    suspend fun upadate(note: note){
        noteDao.update(note.id,note.Title,note.note)
    }
}