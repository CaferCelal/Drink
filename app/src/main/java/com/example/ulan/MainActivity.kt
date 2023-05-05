package com.example.ulan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.mission_view.view.*
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val onePerson = arrayListOf<String>()
    private val twoPerson = arrayListOf<String>()
    private val onePersonPath = "onePersonMissions.txt"
    private val twoPersonPath = "twoPersonMissions.txt"
    private val rnd = Random()
    private  final var blank = "_"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playerList= intent.getStringArrayListExtra("playerList")
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

                missionOnAlertBoxForTwo(finalMission,missionToDelete)
            }
            else{
                finalMission= finalMission.replace(blank,selectOnePlayer(
                    playerList!!
                ))
                missionOnAlertBoxForOne(finalMission,missionToDelete)
            }



        }


    }






















    fun fillList(ListName:ArrayList<String>,path:String){
        var buffer: InputStream = this.assets.open(path)
        var text = buffer.bufferedReader().use{it.readText()}
        var lines    = text.split("\n")
        for (line in lines){
            ListName.add(line)
        }
    }

    fun detectTwoOrOne(mission:String):Boolean{
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

    fun getmission():String{
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


    fun selectOnePlayer(playerList: ArrayList<String>): String {
        var randomPlayer = playerList.get(rnd.nextInt(playerList.size))
        return randomPlayer
    }

    fun selectTwoPlayers(playerList:ArrayList<String>): Array<String> {
        var firstPlayer =selectOnePlayer(playerList)
        var secondPlayer = ""
        do {
             secondPlayer = selectOnePlayer(playerList)

        }while(firstPlayer ==secondPlayer)
        val twoPlayersArray = arrayOf<String>(firstPlayer,secondPlayer)
        return twoPlayersArray
    }


    fun missionOnAlertBoxForOne(mission: String,deleteString: String){
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
            alert.dismiss()
        }


    }
    fun missionOnAlertBoxForTwo(mission: String,deleteString: String){
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
            alert.dismiss()
        }


    }
}