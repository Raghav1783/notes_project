package com.example.notes_project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes_project.Models.note
import com.example.notes_project.databinding.ActivityAddNoteBinding
import com.example.notes_project.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note1: note
    private lateinit var old_note:note
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            old_note=intent.getSerializableExtra("current_note") as note
            binding.etNote.setText(old_note.note)
            binding.etTitle.setText(old_note.Title)
            isUpdate=true
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        binding.imgCheck.setOnClickListener {
            val title=binding.etTitle.text.toString()
            val note= binding.etNote.text.toString()
            if(title.isNotEmpty() || note.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE,d MMM yyyy HH:mm a")
                if (isUpdate){
                    note1= note(old_note.id,title,note,formatter.format(Date()))
                }
                else{
                    note1= note(null,title,note,formatter.format(Date()))


                }
                val intent=Intent()
                intent.putExtra("note",note1)
                setResult(Activity.RESULT_OK,intent)
                onBackPressed()
            }
            else{
                Toast.makeText(this@AddNote,"Please enter some Data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        binding.imgBack.setOnClickListener{
            onBackPressed()
        }
    }
}