package com.example.ulan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shot_counter_view.view.*

class recyclerAdapterForShotCounter(val shotCounterList:ArrayList<Any>)
    :RecyclerView.Adapter<recyclerAdapterForShotCounter.adapterForShotCounter>(){
        class adapterForShotCounter(itemView: View):RecyclerView.ViewHolder(itemView){

        }
    private val constant = shotCounterList.size/2

    override fun getItemCount(): Int {
        return constant
    }

    override fun onBindViewHolder(holder: adapterForShotCounter, position: Int) {
        var i=0
        val playerList = arrayListOf<String>()
        val shotList = arrayListOf<String>()
        while (i<constant){
            playerList.add(shotCounterList.get(i).toString())
            i++
        }
        i=constant
        while (i<shotCounterList.size){
            shotList.add(shotCounterList[i].toString())
            i++
        }
        holder.itemView.playerNameOnShotCounterView.setText(playerList[position])
        holder.itemView.shotDisplayerOnShotCounterView.setText(shotList[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterForShotCounter {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.shot_counter_view,parent,false)
        return adapterForShotCounter(itemView)

    }




}

