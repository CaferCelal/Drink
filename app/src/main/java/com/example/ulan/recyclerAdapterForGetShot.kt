package com.example.ulan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.get_player_alertbox.view.*
import kotlinx.android.synthetic.main.recycler_row_for_add_people.view.*

class recyclerAdapterForGetShot(val requireShotList:ArrayList<String>)
    :RecyclerView.Adapter<recyclerAdapterForGetShot.adapterForGetShot>() {


    class adapterForGetShot(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterForGetShot {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_row_for_add_people, parent, false)
        return adapterForGetShot(itemView)
    }

    override fun onBindViewHolder(holder: adapterForGetShot, position: Int) {
        holder.itemView.playerName.setText(requireShotList.get(position))

        holder.itemView.deleteBtn.setOnClickListener {
            requireShotList.removeAt(position)
            notifyDataSetChanged()
        }
        holder.itemView.penBtn.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            val inflater = LayoutInflater.from(holder.itemView.context)
            val myView = inflater.inflate(R.layout.get_player_alertbox, null)
            builder.setView(myView)
            myView.playerNameOnAlertBox.setText(requireShotList[position])


            val alert = builder.create()
            alert.show()

            myView.checkBtn.setOnClickListener {
                requireShotList.set(position, myView.playerNameOnAlertBox.text.toString())
                notifyDataSetChanged()
                alert.dismiss()
            }
        }
    }

    override fun getItemCount(): Int {
        return requireShotList.size
    }
}



