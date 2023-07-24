package com.example.notes_project.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes_project.Database.NoteRepository
import com.example.notes_project.Database.noteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application){
    private val repository : NoteRepository
    val allnotes : LiveData<List<note>>
     init {
         val dao=noteDatabase.getDatabase(application).noteDao()
         repository=NoteRepository(dao)
         allnotes=repository.allnotes
     }
    fun deleteNote(note: note) = viewModelScope.launch (Dispatchers.IO) {
        repository.delete (note)
    }
    fun insertNote (note: note) = viewModelScope.launch (Dispatchers.IO) {
        repository.insert(note)
    }
    fun updateNote (note: note) = viewModelScope. launch (Dispatchers.IO) {
        repository.upadate(note)
    }
}
