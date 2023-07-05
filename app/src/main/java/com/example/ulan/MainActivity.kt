package com.example.ulan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.mission_view.view.*
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val onePerson = arrayListOf<String>()
    private val twoPerson = arrayListOf<String>()
    private val onePersonPath = "onePersonMissions.txt"
    private val twoPersonPath = "twoPersonMissions.txt"
    private val playerAndShotList = arrayListOf<Any>()
    private val shotAndShotCounterList = arrayListOf<Any>()
    private lateinit var shotCounterAdapter :recyclerAdapterForShotCounter
    private lateinit var layoutManager :LinearLayoutManager
    private val rnd = Random()

    private  final var blank = "_"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playerList= intent.getStringArrayListExtra("playerList")

        var i =0
        while (i<playerList!!.size){
            playerAndShotList.add(playerList.get(i))
            i++
        }
        i=0
        while (i<playerList.size){
            playerAndShotList.add(0)
            i++
        }
        i=0


        shotCounterAdapter = recyclerAdapterForShotCounter(playerAndShotList)
        layoutManager = LinearLayoutManager(this)

        shotCounterRecycler.adapter =shotCounterAdapter
        shotCounterRecycler.layoutManager=layoutManager


        fillList(onePerson,onePersonPath)
        fillList(twoPerson,twoPersonPath)


        findViewById<Button>(R.id.changeBtn).setOnClickListener {
            var finalMission = getmission()
            var missionToDelete = finalMission
            if (detectTwoOrOne(finalMission)) {
                var counter = 0
                var detector = 0
                var i :String
                var twoPlayersArray = selectTwoPlayers(playerList!!)
                    while (counter != finalMission.toCharArray().size-1){

                        i = finalMission.toCharArray().get(counter).toString()

                        if (i == blank) {
                            if (detector == 0) {
                                finalMission =
                                    finalMission.replaceRange(counter, counter + 1, twoPlayersArray[0])
                                    counter+=6
                                detector++
                            } else {
                                finalMission =
                                    finalMission.replaceRange(counter, counter + 1, twoPlayersArray[1])
                            }

                        }
                    counter++
                }

                missionOnAlertBoxForTwo(finalMission,missionToDelete,twoPlayersArray[0])
            }
            else{
                val tempPlayer=selectOnePlayer(playerList!!)
                finalMission= finalMission.replace(blank,tempPlayer)

                missionOnAlertBoxForOne(finalMission,missionToDelete,tempPlayer)
            }



        }


    }

    private fun selectShot():String{


        return ""
    }

    private fun fillList(ListName:ArrayList<String>,path:String){
        var buffer: InputStream = this.assets.open(path)
        var text = buffer.bufferedReader().use{it.readText()}
        var lines    = text.split("\n")
        for (line in lines){
            ListName.add(line)
        }
    }

    private fun detectTwoOrOne(mission:String):Boolean{
        var counter=0
        var i=0
        while (i!=mission.toCharArray().size-1){
            if (blank.equals(mission.toCharArray()[i].toString())){
                counter++
            }
            i++

        }
        if (counter==2){
            return true
        }

        else{
            return false
        }

    }

    private fun getmission():String{
        var desicion = rnd.nextInt(onePerson.size+twoPerson.size-2)
        var mission =""
        if (onePerson.isEmpty()){
            mission = twoPerson[rnd.nextInt(twoPerson.size-1)]
            return mission
        }
        if (twoPerson.isEmpty()){
            mission = onePerson[rnd.nextInt(onePerson.size-1)]
            return mission
        }
        if(onePerson.isNotEmpty() || twoPerson.isNotEmpty()){
        if (onePerson.size>twoPerson.size){
            if (desicion>twoPerson.size-1){
                mission = onePerson[rnd.nextInt(onePerson.size-1)]
            }
            else{
                mission = twoPerson[rnd.nextInt(twoPerson.size-1)]
            }
        }
        else{
            if (desicion>onePerson.size-1){
                mission = twoPerson[rnd.nextInt(twoPerson.size-1)]
            }
            else{
                mission = onePerson[rnd.nextInt(onePerson.size-1)]
            }
        }

        }

        return mission

    }


    private fun selectOnePlayer(playerList: ArrayList<String>): String {
        var randomPlayer = playerList.get(rnd.nextInt(playerList.size))
        return randomPlayer
    }

    private fun selectTwoPlayers(playerList:ArrayList<String>): Array<String> {
        var firstPlayer =selectOnePlayer(playerList)
        var secondPlayer = ""
        do {
             secondPlayer = selectOnePlayer(playerList)

        }while(firstPlayer ==secondPlayer)
        val twoPlayersArray = arrayOf<String>(firstPlayer,secondPlayer)
        return twoPlayersArray
    }


    private fun missionOnAlertBoxForOne(mission: String,deleteString: String,drinker :String){
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val View = inflater.inflate(R.layout.mission_view, null)
        builder.setView(View)
        val alert = builder.create()
        View.missionTextView.setText(mission)

        alert.show()
        View.doBtn.setOnClickListener {
            onePerson.remove(deleteString)
            alert.dismiss()
        }
        View.drinkBtn.setOnClickListener {
            var i=0
            var detect =0
            while (i<playerAndShotList.size/2){
                if (playerAndShotList.get(i).toString().equals(drinker)){
                    detect =i
                }
                i++
            }
            playerAndShotList.set(detect+playerAndShotList.size/2,
                playerAndShotList.get(detect+playerAndShotList.size/2).toString().toInt()+1)
            shotCounterAdapter.notifyDataSetChanged()
            alert.dismiss()
        }


    }
    private fun missionOnAlertBoxForTwo(mission: String,deleteString: String,drinker: String){
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val View = inflater.inflate(R.layout.mission_view, null)
        View.missionTextView.setText(mission)
        builder.setView(View)
        val alert = builder.create()


        alert.show()
        View.doBtn.setOnClickListener {
            twoPerson.remove(deleteString)
            alert.dismiss()
        }
        View.drinkBtn.setOnClickListener {
            var i=0
            var detect =0
            while (i<playerAndShotList.size/2){
                if (playerAndShotList.get(i).toString().equals(drinker)){
                    detect =i
                }
                i++
            }
            playerAndShotList.set(detect+playerAndShotList.size/2,
                playerAndShotList.get(detect+playerAndShotList.size/2).toString().toInt()+1)
            shotCounterAdapter.notifyDataSetChanged()
            alert.dismiss()
        }


    }
}