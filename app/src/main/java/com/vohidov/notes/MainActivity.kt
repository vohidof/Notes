package com.vohidov.notes

import Adapter.ItemClick
import Adapter.RvAdapter
import Model.NoteModel
import Utils.MyDbHelper
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_layout.*

class MainActivity : AppCompatActivity() {

    lateinit var list: ArrayList<NoteModel>
    lateinit var adapter: RvAdapter
    lateinit var myDbHelper: MyDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDbHelper = MyDbHelper(this)

        btn_add.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        edt_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        list = ArrayList()
        list.addAll(myDbHelper.getAllNotes())

        adapter = RvAdapter(this, list, object : ItemClick {
            override fun onClick(list: ArrayList<NoteModel>, position: Int) {
                val intent = Intent(this@MainActivity, ShowActivity::class.java)
                intent.putExtra("title", list[position].title)
                intent.putExtra("desc", list[position].desc)
                startActivity(intent)
            }

            override fun deleteItem(noteModel: NoteModel) {
                val dialog = AlertDialog.Builder(this@MainActivity)

                dialog.setMessage("You want delete your note?")

                dialog.setPositiveButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }
                })

                dialog.setNegativeButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        myDbHelper.deleteNotes(noteModel)
                        onResume()
                        Toast.makeText(this@MainActivity, "Deleted", Toast.LENGTH_SHORT).show()
                    }
                })
                dialog.show()
            }

            @SuppressLint("InflateParams")
            override fun editUser(noteModel: NoteModel) {
                val dialog = BottomSheetDialog(this@MainActivity)

                dialog.setContentView(
                    layoutInflater.inflate(
                        R.layout.add_layout,
                        null,
                        false
                    )
                )
                dialog.edt_title2.setText(noteModel.title)
                dialog.edt_desc2.setText(noteModel.desc)

                dialog.btn_save2.setOnClickListener {
                    noteModel.title = dialog.edt_title2.text.toString()
                    noteModel.desc = dialog.edt_desc2.text.toString()

                    myDbHelper.updateNotes(noteModel)
                    onResume()
                    Toast.makeText(this@MainActivity, "Update!", Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                }

                dialog.show()
            }
        })

        adapter.notifyDataSetChanged()
        RecyclerView.adapter = adapter
    }

    private fun filter(toString: String) {
        val filteredList: ArrayList<NoteModel>
        filteredList = ArrayList()

        for (item in list) {
            if (item.title.toLowerCase().contains(toString.toLowerCase())) {
                filteredList.add(item)
                filteredList.sortBy { noteModel -> noteModel.title }
            }
        }

        adapter.filterList(filteredList)
    }
}