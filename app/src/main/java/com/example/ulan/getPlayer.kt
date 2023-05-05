package com.example.ulan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_get_player.*
import kotlinx.android.synthetic.main.get_player_alertbox.view.*
import kotlinx.android.synthetic.main.recycler_row_for_add_people.view.*

class getPlayer : AppCompatActivity() {
    val playerList = arrayListOf<String>()
    private val recyclerAdapter =recyclerAdapterForGetPlayer(playerList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_player)

        val layoutManager = LinearLayoutManager(this)
        getPlayerRecycler.adapter = recyclerAdapter
        getPlayerRecycler.layoutManager =layoutManager

        addBtn.setOnClickListener{
            if (recyclerAdapter.requirePlayerList.size<6){
                val builder = AlertDialog.Builder(this)
                val inflater = LayoutInflater.from(this)
                val view = inflater.inflate(R.layout.get_player_alertbox, null)

                builder.setView(view)

                val alert = builder.create()
                alert.show()

                view.checkBtn.setOnClickListener{
                    playerList.add(view.playerNameOnAlertBox.text.toString())
                    alert.dismiss()
                }
            }
            else{
                val toast = Toast.makeText(this,"En fazla 6 kişi oynayabilir",Toast.LENGTH_SHORT)
                toast.show()
            }

        }
        playBtn.setOnClickListener {
            if (playerList.size >1){
                val intent = Intent(this,MainActivity::class.java)
                intent.putStringArrayListExtra("playerList",playerList)
                startActivity(intent)
            }
            else{
                val toast = Toast.makeText(this,"En az 2 kişi oynayabilir",Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }





}


