package com.example.notes_project.Adapter

import android.content.Context
import android.os.Build
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_project.Models.note
import com.example.notes_project.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class NoteAdapter(private val context: Context,val listener:NoteslickListener): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val NotesList = ArrayList<note>()
    private val FullList = ArrayList<note>()




    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote=NotesList[position]
        holder.title.text=currentNote.Title
        holder.title.isSelected=true

        holder.note_tv.text=currentNote.note
        holder.date.text=currentNote.date
        holder.title.isSelected=true
        holder.notes_layout.setCardBackgroundColor (holder.itemView.resources.getColor(randomColour(), null))
        holder.notes_layout.setOnClickListener{
            listener.onitemClicked(NotesList[holder.adapterPosition])
        }
        holder.notes_layout.setOnLongClickListener{
            listener.onlongitemClicked(NotesList[holder.adapterPosition],holder.notes_layout)
            true
        }
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }
    fun updateList(newList: List<note>){

        FullList.clear()
        FullList.addAll(newList)
        NotesList.clear()
        NotesList.addAll(FullList)
        notifyDataSetChanged()
    }
    fun filterList(search:String){
        NotesList.clear()
        for (item in FullList){
              if(item.Title?.lowercase()?.contains(search.lowercase())==true ||
                  item.note?.lowercase()?.contains(search.lowercase())==true){
                  NotesList.add(item)


                  }

        }
        notifyDataSetChanged()
    }
    fun randomColour():Int{
        val list=ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)
        val seed=System.currentTimeMillis().toInt()
        val randomIndex= Random(seed).nextInt(list.size)
        return list[randomIndex]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.list,parent,false))
    }
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title= itemView.findViewById<TextView>(R.id.title)
        val note_tv= itemView.findViewById<TextView>(R.id.tv_note)
        val date= itemView.findViewById<TextView>(R.id.tv_date)

    }
    interface NoteslickListener{
        fun onitemClicked(note:note)
        fun onlongitemClicked(note:note,cardView: CardView)
    }
}
