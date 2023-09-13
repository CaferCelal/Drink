package com.example.ulan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_get_player.addBtn
import kotlinx.android.synthetic.main.activity_get_player.playBtn
import kotlinx.android.synthetic.main.activity_get_shot.*
import kotlinx.android.synthetic.main.get_player_alertbox.view.*

class getShot : AppCompatActivity() {
    val shotList = arrayListOf<String>()
    private val recyclerAdapter =recyclerAdapterForGetShot(shotList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_shot)

        val playerList= intent.getStringArrayListExtra("playerList")

        val layoutManager = LinearLayoutManager(this)
        getShotRecycler.adapter = recyclerAdapter
        getShotRecycler.layoutManager =layoutManager

        addBtn.setOnClickListener{
            if (recyclerAdapter.requireShotList.size<6){
                val builder = AlertDialog.Builder(this)
                val inflater = LayoutInflater.from(this)
                val view = inflater.inflate(R.layout.get_player_alertbox, null)

                builder.setView(view)

                val alert = builder.create()
                alert.show()

                view.checkBtn.setOnClickListener{
                    shotList.add(view.playerNameOnAlertBox.text.toString())
                    alert.dismiss()
                }
            }
            else{
                val toast = Toast.makeText(this,"En fazla 6 farklı içki kullanılabilir", Toast.LENGTH_SHORT)
                toast.show()
            }

        }
        playBtn.setOnClickListener {
            if (shotList.size >0){
                val intent = Intent(this,MainActivity::class.java)
                intent.putStringArrayListExtra("playerList",playerList)
                intent.putStringArrayListExtra("shotList",shotList)
                startActivity(intent)
                finish()
            }
            else{
                val toast = Toast.makeText(this,"En az 1 içki bulunmalıdır", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }
    }
