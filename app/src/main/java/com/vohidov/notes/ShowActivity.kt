package com.vohidov.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")

        txt_titleShow.text = title
        txt_descShow.text = desc
    }
}