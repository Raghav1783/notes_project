 package com.example.notes_project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView

import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes_project.Adapter.NoteAdapter
import com.example.notes_project.Database.noteDatabase
import com.example.notes_project.Models.NoteViewModel
import com.example.notes_project.Models.note
import com.example.notes_project.databinding.ActivityMainBinding


 class MainActivity : AppCompatActivity(),NoteAdapter.NoteslickListener,PopupMenu.OnMenuItemClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var database:noteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var selectedNote:note
    lateinit var adapter: NoteAdapter

     private val updateNote =registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
         if (result.resultCode == Activity.RESULT_OK) {
             val note = result.data?.getSerializableExtra("note") as? note
             if (note != null) {
                 viewModel.updateNote(note)
             }
         }
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        viewModel=ViewModelProvider( this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allnotes.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }

        database= noteDatabase.getDatabase(this)
    }

     private fun initUI() {
         binding.recyclerView.setHasFixedSize(true)
         binding.recyclerView.layoutManager=StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
         adapter= NoteAdapter(this,this)
         binding.recyclerView.adapter=adapter
         val getContent=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
             if(result.resultCode== Activity.RESULT_OK){
                 val note=result.data?.getSerializableExtra("note") as? note
                 if(note!=null){
                     viewModel.insertNote(note)
                 }

             }


         }
         binding.fbAddNote.setOnClickListener {
             val intent=Intent(this,AddNote::class.java)
             getContent.launch(intent)
         }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText !=null){
                    adapter.filterList(newText)
                }
                return true
            }
        } )

     }

     override fun onitemClicked(note: note) {
         val intent=Intent(this@MainActivity,AddNote::class.java)
         intent.putExtra("current_note",note)
         updateNote.launch(intent)
     }

     override fun onlongitemClicked(note: note, cardView: CardView) {
         selectedNote=note
         popUpDisplay(cardView)
     }
     private fun popUpDisplay(cardView: CardView){
         val popup=PopupMenu(this,cardView)
         popup.setOnMenuItemClickListener(this@MainActivity)
         popup.inflate(R.menu.pop_up_menu)
         popup.show()
     }

     override fun onMenuItemClick(item: MenuItem?): Boolean {
         if(item?.itemId ==R.id.delete_note){
             viewModel.deleteNote(selectedNote)
             return true
         }
         return false
     }
 }