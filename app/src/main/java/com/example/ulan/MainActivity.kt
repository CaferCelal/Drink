package com.example.ulan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.basic_textview_alert.view.*
import kotlinx.android.synthetic.main.basic_textview_alert.view.ok
import kotlinx.android.synthetic.main.mission_view.view.*
import java.io.InputStream
import kotlin.collections.ArrayList
import kotlin.random.Random
class MainActivity : AppCompatActivity() {
    // the arraylist that holds missions for first person
    private val onePerson = arrayListOf<String>()
    private val twoPerson = arrayListOf<String>()
    private val onePersonPath = "onePersonMissions.txt"
    private val twoPersonPath = "twoPersonMissions.txt"
    private val playerAndShotList = arrayListOf<Any>()
    private val playerAndPlayerCounterList = arrayListOf<Player>()
    private val shotAndShotCounterList = arrayListOf<Any>()
    private var onBackPressCounter : Long=0


    private lateinit var shotCounterAdapter :recyclerAdapterForShotCounter
    private lateinit var layoutManager :LinearLayoutManager
    private val rnd = Random

    private  final var blank = "_"





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playerList= intent.getStringArrayListExtra("playerList")
        val shotList = intent.getStringArrayListExtra("shotList")
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
        while (i<shotList!!.size){
            shotAndShotCounterList.add(shotList.get(i))
            i++
        }
        i=0
        while (i<shotList.size){
           shotAndShotCounterList.add(1)
           i++
        }
        i=0
        while (i<playerList.size){
            playerAndPlayerCounterList.add(Player(playerList[i].toString(),1))
            i++
        }


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
                var twoPlayersArray = selectTwoPlayers()
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
                incrementPlayerCounter(twoPlayersArray[0])
                missionOnAlertBoxForTwo(finalMission,missionToDelete,twoPlayersArray[0])
            }
            else{
                val tempPlayer=selectOnePlayer(playerAndPlayerCounterList)
                incrementPlayerCounter(tempPlayer)
                finalMission= finalMission.replace(blank,tempPlayer)

                missionOnAlertBoxForOne(finalMission,missionToDelete,tempPlayer)
            }


        }


    }

    override fun onBackPressed() {

        if (onBackPressCounter +1000 >= System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(this,"Çıkmak için tekrar bas",Toast.LENGTH_SHORT).show()
        }
        onBackPressCounter = System.currentTimeMillis()
    }



    /**
     * description
     * @param requireShotList [ArrayList]
     * @return [String] selected shot
     */
    private fun selectShot(requireShotList:ArrayList<Any>):String{
        var a=0
        var b=1
        var count =0
        val shotList = arrayListOf<String>()
        val shotCounterList = arrayListOf<Int>()
        while (a<requireShotList.size/2){
            shotList.add(requireShotList.get(a).toString())
            a++
        }
        a=requireShotList.size/2
        while (a<requireShotList.size){
            shotCounterList.add(requireShotList.get(a).toString().toInt())
            a++
        }
        shotCounterList.sort()
        val sum =shotCounterList.size*(shotCounterList.size+1)/2
        val randomDesicion =rnd.nextInt(1,sum+1)
        a=0

        while (true) {
            when (randomDesicion) {
                in sum -a..sum ->
                    return shotList.get(shotList.size-1-count)
            }
            b++
            a+=b
            count++
            if (a>sum){
                break
            }
        }
        return "error"
    }

    private fun selectOnePlayer(playerList: ArrayList<Player>): String {
        val rnd = Random(System.currentTimeMillis()) // Initialize random number generator

        val sortedPlayers = playerList.sortedByDescending { it.shotCount } // Sort players by shot count in descending order

        val sum = (1..sortedPlayers.size).sum() // Calculate the sum of natural numbers up to the number of players

        val randomDecision = rnd.nextInt(1, sum + 1) // Generate a random decision

        var accumulatedSum = 0
        for ((index, player) in sortedPlayers.withIndex()) {
            accumulatedSum += (index + 1) // Incrementally add the current player's index to the accumulated sum

            if (randomDecision <= accumulatedSum) {
                return player.playerName
            }
        }

        return "error"
    }




    private fun selectTwoPlayers(): Array<String> {
        val firstPlayer =selectOnePlayer(playerAndPlayerCounterList)
        var secondPlayer = ""
        do {
            secondPlayer = selectOnePlayer(playerAndPlayerCounterList)

        }while(firstPlayer ==secondPlayer)
        val twoPlayersArray = arrayOf<String>(firstPlayer,secondPlayer)
        return twoPlayersArray
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







    private fun incrementShotCounter(requireShotName:String){
        var i=0
        while (i<shotAndShotCounterList.size/2){
            if (shotAndShotCounterList.get(i).toString().equals(requireShotName)){
                shotAndShotCounterList.set(shotAndShotCounterList.size/2+i,
                    shotAndShotCounterList.get(shotAndShotCounterList.size/2+i).toString().toInt() +1)
            }
            i++
        }

    }

    private fun incrementPlayerCounter(playerName: String) {
        var i=0

        while (i<playerAndPlayerCounterList.size){
            if (playerName == playerAndPlayerCounterList[i].playerName){
                playerAndPlayerCounterList[i].shotCount++
            }
            i++
        }
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
            alert.dismiss()
            var shot =selectShot(shotAndShotCounterList)
            var i=0
            var detect =0
            var sentence =""
            while (i<playerAndShotList.size/2){
                if (playerAndShotList.get(i).toString().equals(drinker)){
                    detect =i
                }
                i++
            }

            playerAndShotList.set(detect+playerAndShotList.size/2,
                playerAndShotList.get(detect+playerAndShotList.size/2).toString().toInt()+1)
            incrementShotCounter(shot)
            sentence = drinker + " bir shot " + shot + " atsın."
            val Builder = AlertDialog.Builder(this)
            val Inflater = LayoutInflater.from(this)
            val View = Inflater.inflate(R.layout.basic_textview_alert, null)
            Builder.setView(View)
            View.basicText.setText(sentence)
            View.ok.setOnClickListener{

            }
            val Alert=Builder.create()
            View.ok.setOnClickListener{
                Alert.dismiss()
            }
            Alert.show()


            shotCounterAdapter.notifyDataSetChanged()

        }

    }
    private fun missionOnAlertBoxForTwo(mission: String,deleteString: String,drinker: String){
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.mission_view, null)
        view.missionTextView.setText(mission)
        builder.setView(view)
        val alert = builder.create()


        alert.show()
        view.doBtn.setOnClickListener {
            twoPerson.remove(deleteString)
            alert.dismiss()
        }
        view.drinkBtn.setOnClickListener {
            alert.dismiss()
            var shot =selectShot(shotAndShotCounterList)
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

            incrementShotCounter(shot)
            var sentence = drinker + " bir shot " + shot + " atsın."
            val Builder = AlertDialog.Builder(this)
            val Inflater = LayoutInflater.from(this)
            val View =Inflater.inflate(R.layout.basic_textview_alert,null)
            Builder.setView(View)
            val Alert =Builder.create()
            View.basicText.setText(sentence)
            View.ok.setOnClickListener{
                Alert.dismiss()
            }
            Alert.show()


            shotCounterAdapter.notifyDataSetChanged()
        }

    }

}