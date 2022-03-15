package com.example.computationsskills

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.util.*

class GameActivity : AppCompatActivity() {

    lateinit var preference : SharedPreferences

    var operators = listOf("+", "-","*","/")
    var checkOptions = listOf("greater", "equal", "less")
    var termCount1 : Int? = 0
    var termCount2 : Int? = 0
    var firstTerm1 : String? = null
    var firstTerm2 : String? = null
    var valueOfExpression1: Int = 0
    var valueOfExpression2: Int = 0
    var expression1: String?=null
    var expression2: String?=null
    var timerStartValue = 5000000000
    var correctCount : Int = 0
    var inCorrectCount : Int = 0
    var timerCorrectCount : Int = 0
    var seconds = 50


    lateinit var showExpression1 : TextView
    lateinit var showExpression2 : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val btnGreater = findViewById<Button>(R.id.btnGreatrer)
        val btnEqual = findViewById<Button>(R.id.btnEqual)
        val btnLesser = findViewById<Button>(R.id.btnLess)
         showExpression1 = findViewById<TextView>(R.id.txtExpression1)
         showExpression2 = findViewById<TextView>(R.id.txtExpression2)
        val textResult = findViewById<TextView>(R.id.txtResult)

        val scoreboard = findViewById<LinearLayout>(R.id.scoreboard)

//        val showCorrect = findViewById<TextView>(R.id.correct)
//        val showIncorrect = findViewById<TextView>(R.id.incorrect)

//showCorrect,showIncorrect, btnGreater ,btnEqual ,btnLesser  , showExpression1 ,showExpression2 ,textResult ,scoreboard
        scoreboard.visibility = View.GONE
        runTheGame(showExpression1,1)
        runTheGame(showExpression2,2)
        timerStart()

        btnGreater.setOnClickListener {
            check(checkOptions[0],textResult)
            runTheGame(showExpression1,1)
            runTheGame(showExpression2,2)
        }

        btnEqual.setOnClickListener {
            check(checkOptions[1],textResult)
            runTheGame(showExpression1,1)
            runTheGame(showExpression2,2)
        }

        btnLesser.setOnClickListener {
            check(checkOptions[2],textResult)
            runTheGame(showExpression1,1)
            runTheGame(showExpression2,2)
        }
    }

    override fun onSaveInstanceState(outState : Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("correct",correctCount)
        outState.putInt("incorrect",inCorrectCount)
        outState.putInt("timerCorrectCount",timerCorrectCount)
        outState.putInt("seconds",seconds)



        outState.putInt("value1",valueOfExpression1)
        outState.putInt("value2",valueOfExpression2)
        outState.putString("expression1",expression1)

        outState.putString("expression2",expression2)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        correctCount = savedInstanceState.getInt("correct",0)
        inCorrectCount = savedInstanceState.getInt("incorrect",0)
        timerCorrectCount = savedInstanceState.getInt("timerCorrectCount",0)
        seconds = savedInstanceState.getInt("seconds", 50)

        valueOfExpression1 = savedInstanceState.getInt("value1", 0)
        valueOfExpression2 = savedInstanceState.getInt("value2", 0)
        expression1 = savedInstanceState.getString("expression1", null)
        expression2 = savedInstanceState.getString("expression2", null)

        showExpression1.text= expression1
        showExpression2.text= expression2

    }


    fun runTheGame(showExpression:TextView, expressionNumber: Int) {
        when(expressionNumber) {
            1 -> {
                firstTerm1 = generateFirstNumber().toString()
                termCount1 = generateNumberOfTerms()
                generateExpression(firstTerm1!!,showExpression, termCount1!!, firstTerm1!!.toInt(),expressionNumber)
            }
            2 -> {
                firstTerm2 = generateFirstNumber().toString()
                termCount2 = generateNumberOfTerms()
                generateExpression(firstTerm2!!,showExpression, termCount2!!, firstTerm2!!.toInt(),expressionNumber)
            }
        }
    }


    fun generateFirstNumber(): Int {
        return randomOneToTwenty()
    }

    fun generateNumberOfTerms(): Int {
        return randomOneToThree()
    }

    fun generateExpression(firstTerm1:String,showExpression:TextView,termCount1:Int, final :Int, expressionNumber : Int) {
        var finalValue = final
        var termCount = termCount1
        var operator = randomUpToFour()
        var expressionNo = expressionNumber
        var expressions: String? = null
        var secondTerm: Int

        while (true) {
            secondTerm = randomOneToTwenty()
            when (operator) {
                0 -> {
                    if((finalValue + secondTerm) <= 100){
                        finalValue += secondTerm
                        break
                    }else{
                        continue
                    }

                }
                1 -> {
                    if((finalValue - secondTerm) <= 100){
                        finalValue -= secondTerm
                        break
                    }else{
                        continue
                    }

                }
                2 -> {
                    if((finalValue * secondTerm) <= 100){
                        finalValue *= secondTerm
                        break
                    }else{
                        continue
                    }
                }
                3 -> {
                    if ((finalValue % secondTerm == 0) && ((finalValue / secondTerm) <= 100)){
                        finalValue /= secondTerm
                        break
                    }else{
                        continue
                    }
                }
            }
        }
        when (expressionNo){
            1 -> {
                expression1 = firstTerm1 + operators[operator] + secondTerm
                expressions = expression1
            }
            2 -> {
                expression2 = firstTerm1 + operators[operator] + secondTerm
                expressions = expression2
            }
        }


        termCount -= 1

        if (termCount > 0){
            generateExpression("($expressions)",showExpression,termCount,finalValue,expressionNo)

        }else{
            when (expressionNo){
                1 -> {valueOfExpression1 = finalValue}
                2 -> {valueOfExpression2 = finalValue}
            }
            showExpression.text = expressions
        }


        Log.d("expo 1 ", "$expression1 = $valueOfExpression1" )
        Log.d("expo 2 ", "$expression2 = $valueOfExpression2")

    }

    @SuppressLint("SetTextI18n")
    fun check(checkOption: String, txtResult :TextView){
        when (checkOption) {
            "greater" -> {
                if (valueOfExpression1 > valueOfExpression2){
                    txtResult.text = "CORRECT!"
                    correctCount +=1
                    timerCorrectCount += 1
                    txtResult.setTextColor(Color.GREEN)
                }else{
                    txtResult.text = "WRONG!!"
                    inCorrectCount += 1
                    txtResult.setTextColor(Color.RED)
                }
            }
            "equal" -> {
                if (valueOfExpression1 == valueOfExpression2){
                    txtResult.text = "CORRECT!"
                    correctCount +=1
                    timerCorrectCount += 1
                    txtResult.setTextColor(Color.GREEN)
                }else{
                    txtResult.text = "WRONG!!"
                    inCorrectCount += 1
                    txtResult.setTextColor(Color.RED)
                }
            }
            "less" -> {
                if (valueOfExpression1 < valueOfExpression2){
                    txtResult.text = "CORRECT!"
                    correctCount +=1
                    timerCorrectCount += 1
                    txtResult.setTextColor(Color.GREEN)
                }else{
                    txtResult.text = "WRONG!!"
                    inCorrectCount += 1
                    txtResult.setTextColor(Color.RED)
                }
            }
        }
    }

//showCorrect:TextView, showIncorrect: TextView, btnGreater :Button, btnEqual : Button, btnLesser : Button, showExpression1 :TextView, showExpression2 :TextView, textResult :TextView, scoreboard :LinearLayout
    private fun timerStart(){
        val timer = findViewById<TextView>(R.id.timer)
        object : CountDownTimer(timerStartValue.toLong(), 1000) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {

                if(timerCorrectCount == 2) { // reminder : change 2 to 5 -> 2 is used in debug purpose
                    timerCorrectCount = 0
                    seconds += 10
                }

                if (seconds > 30){
                    timer.setTextColor(Color.GREEN)
                }else if (seconds > 10){
                    timer.setTextColor(Color.YELLOW)
                    timer.textSize = 20F
                } else{
                    timer.setTextColor(Color.RED)
                    timer.textSize = 21F
                }

                timer.text = "Seconds remaining\n0 : $seconds"

                if (seconds == 0){
                    cancel()
                    onFinish()
                }
                seconds --
            }


            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                timer.setTextColor(Color.YELLOW)
                timer.text = "Game-Over!"
                timer.textSize = 30F
                showScore()
            }
        }.start()
    }

     fun showScore(){
         var scoreActivityIntent = Intent(this,ScoreActivity::class.java)
         scoreActivityIntent.putExtra("correct",correctCount)
         scoreActivityIntent.putExtra("incorrect",inCorrectCount)
         Toast.makeText(this, "Moving on...", Toast.LENGTH_SHORT).show()
         startActivity(scoreActivityIntent)
     }


    fun randomUpToFour(): Int {
        return Random().nextInt(4)
    }

    fun randomOneToThree(): Int {
        return 1+ Random().nextInt(3)
    }

    fun randomOneToTwenty(): Int {
        return 1+ Random().nextInt(20)
    }

}