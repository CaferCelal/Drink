package com.example.ulan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.basic_textview_alert.view.*

class welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        start.setOnClickListener {
            val intent =Intent(this,getPlayer::class.java)
            startActivity(intent)
        }
        settings.setOnClickListener{
            val intent =Intent(this,Settings::class.java)
            startActivity(intent)
        }
        howToPlay.setOnClickListener{
            howToPlayAction()
        }
        exit.setOnClickListener{
            finish()
        }
    }







    private fun howToPlayAction() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.basic_textview_alert, null)
        builder.setView(view)
        val alert = builder.create()
        view.basicText.setText(R.string.thowToPlay)
        alert.show()

        view.ok.setOnClickListener{
            alert.dismiss()
        }
    }
}