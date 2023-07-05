package com.example.ulan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.basic_textview_alert.view.*
import kotlinx.android.synthetic.main.language.view.*
import kotlinx.android.synthetic.main.voice.view.*
import kotlinx.android.synthetic.main.voice.view.ok

class Settings : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        language.setOnClickListener {
            languageAction()
        }
        voice.setOnClickListener {
            voiceAction()
        }
        privacy.setOnClickListener {
            privacyAction()
        }
        contact.setOnClickListener {
            contactAction()
        }
        exit.setOnClickListener{
            exitAction()
        }

    }

    private fun exitAction() {
        val intent =Intent(this,welcome::class.java)
        startActivity(intent)
    }

    private fun contactAction() {
        val builder =AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.basic_textview_alert,null)
        builder.setView(view)
        view.basicText.setText(R.string.tcontact)

        val alert =builder.create()
        alert.show()

        view.ok.setOnClickListener {
            alert.dismiss()
        }
    }


    private fun privacyAction() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.basic_textview_alert, null)
        builder.setView(view)
        view.basicText.setText(R.string.tprivacy)


        val alert =builder.create()
        alert.show()

        view.ok.setOnClickListener {
            alert.dismiss()
        }

    }


    private fun voiceAction(){
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.voice, null)
        builder.setView(view)

        val alert = builder.create()
        alert.show()
        view.music.setOnClickListener {
            val music = view.findViewById<ImageButton>(R.id.music)
        }
        view.audio.setOnClickListener{
            val audio=view.findViewById<ImageButton>(R.id.audio)
            audio.setImageResource(R.drawable.noaudio)
        }
        view.ok.setOnClickListener{
            alert.dismiss()
        }

    }

    private fun languageAction(){
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.language, null)
        builder.setView(view)

        val alert = builder.create()
        alert.show()

        view.turkish.setOnClickListener {
            val toast = Toast.makeText(this,"Dil Türkçe olarak ayarlandı",Toast.LENGTH_SHORT)
            toast.show()
            alert.dismiss()
        }
        view.english.setOnClickListener {
            val toast = Toast.makeText(this,"Coming Soon !",Toast.LENGTH_SHORT)
            toast.show()
            alert.dismiss()
        }
        view.ok.setOnClickListener {
            alert.dismiss()
        }
    }
}