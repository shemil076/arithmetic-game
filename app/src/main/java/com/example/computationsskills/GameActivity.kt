package com.example.computationsskills

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*

class GameActivity : AppCompatActivity() {

    var operators = listOf("+", "-","*","/")
    var checkOptions = listOf("greater", "equal", "less")
    var termCount1 : Int? = 0
    var termCount2 : Int? = 0
    var firstTerm1 : String? = null
    var firstTerm2 : String? = null
    var valueOfExpression1: Int? = 0
    var valueOfExpression2: Int? = 0
    var expression1: String?=null
    var expression2: String?=null
    var timerStartValue = 50000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val btnGreater = findViewById<Button>(R.id.btnGreatrer)
        val btnEqual = findViewById<Button>(R.id.btnEqual)
        val btnLesser = findViewById<Button>(R.id.btnLess)
        val showExpression1 = findViewById<TextView>(R.id.txtExpression1)
        val showExpression2 = findViewById<TextView>(R.id.txtExpression2)
        val textResult = findViewById<TextView>(R.id.txtResult)
        val timer = findViewById<TextView>(R.id.timer)

//        firstTerm1 = generateFirstNumber().toString()
//        firstTerm2 = generateFirstNumber().toString()
//        termCount1 = generateNumberOfTerms()
//        termCount2 = generateNumberOfTerms()
//
//        generateExpression(firstTerm1!!,showExpression1, termCount1!!, firstTerm1!!.toInt(),1)
//        generateExpression(firstTerm2!!,showExpression2, termCount2!!, firstTerm2!!.toInt(),2)
//        values()


        runTheGame(showExpression1,1)
        runTheGame(showExpression2,2)

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

        object : CountDownTimer(timerStartValue.toLong(), 1000) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val min = (timerStartValue / 1000) / 60
                val sec = millisUntilFinished / 1000
                if (sec > 30){
                    timer.setTextColor(Color.GREEN)
                }else if (sec > 10){
                    timer.setTextColor(Color.YELLOW)
                } else{
                    timer.setTextColor(Color.RED)
                }

                timer.text = "$min : $sec"
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                timer.text = "done!"
                btnGreater.visibility = View.GONE
                btnEqual.visibility = View.GONE
                btnLesser.visibility = View.GONE
                showExpression1.visibility = View.GONE
                showExpression2.visibility = View.GONE

            }
        }.start()


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
                if (valueOfExpression1!! > valueOfExpression2!!){
                    txtResult.text = "CORRECT!"
                    txtResult.setTextColor(Color.GREEN)
                }else{
                    txtResult.text = "WRONG!!"
                    txtResult.setTextColor(Color.RED)
                }
            }
            "equal" -> {
                if (valueOfExpression1!! == valueOfExpression2!!){
                    txtResult.text = "CORRECT!"
                    txtResult.setTextColor(Color.GREEN)
                }else{
                    txtResult.text = "WRONG!!"
                    txtResult.setTextColor(Color.RED)
                }
            }
            "less" -> {
                if (valueOfExpression1!! < valueOfExpression2!!){
                    txtResult.text = "CORRECT!"
                    txtResult.setTextColor(Color.GREEN)
                }else{
                    txtResult.text = "WRONG!!"
                    txtResult.setTextColor(Color.RED)
                }
            }
        }
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