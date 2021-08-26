package com.vohidov.notes

import Model.NoteModel
import Utils.MyDbHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    lateinit var myDbHelper: MyDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        myDbHelper = MyDbHelper(this)

        btn_save.setOnClickListener {
            val title = edt_title.text.toString()
            val desc = edt_desc.text.toString()

            val note = NoteModel(title, desc)
            myDbHelper.addNotes(note)
            Toast.makeText(this, "Saved note $title", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}