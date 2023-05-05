package com.example.ulan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.get_player_alertbox.view.*
import kotlinx.android.synthetic.main.recycler_row_for_add_people.view.*
import java.nio.file.attribute.AclEntry.Builder

class recyclerAdapterForGetPlayer(val requirePlayerList :ArrayList<String>)
    :RecyclerView.Adapter<recyclerAdapterForGetPlayer.adapterForGetPlayer>(){

    class adapterForGetPlayer(itemView: View):RecyclerView.ViewHolder(itemView){

        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterForGetPlayer {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_for_add_people,parent,false)
        return adapterForGetPlayer(itemView)
    }


    override fun getItemCount(): Int {
        return requirePlayerList.size
    }

    override fun onBindViewHolder(holder: adapterForGetPlayer, position: Int) {
        holder.itemView.playerName.setText(requirePlayerList.get(position))

        holder.itemView.deleteBtn.setOnClickListener {
                requirePlayerList.removeAt(position)
                notifyDataSetChanged()
        }
        holder.itemView.penBtn.setOnClickListener{
            val builder = AlertDialog.Builder(holder.itemView.context)
            val inflater = LayoutInflater.from(holder.itemView.context)
            val myView = inflater.inflate(R.layout.get_player_alertbox, null)
            builder.setView(myView)
            myView.playerNameOnAlertBox.setText(requirePlayerList[position])


            val alert = builder.create()
            alert.show()

            myView.checkBtn.setOnClickListener{
                requirePlayerList.set(position,myView.playerNameOnAlertBox.text.toString())
                notifyDataSetChanged()
                alert.dismiss()
            }

        }
    }
}








