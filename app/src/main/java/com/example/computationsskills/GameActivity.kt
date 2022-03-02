package com.example.computationsskills

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.*

class GameActivity : AppCompatActivity() {

    var operators = listOf("+", "-","*","/")
    var termCount1 : Int? = 0
    var termCount2 : Int? = 0
    var firstTerm1 : String? = null
    var firstTerm2 : String? = null
    var valueOfExpression1: Int? = 0
    var valueOfExpression2: Int? = 0
    var expression1: String?=null
    var expression2: String?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val btnGreater = findViewById<Button>(R.id.btnGreatrer)
        val btnEqual = findViewById<Button>(R.id.btnEqual)
        val btnLesser = findViewById<Button>(R.id.btnLess)
        val showExpression1 = findViewById<TextView>(R.id.txtExpression1)
        val showExpression2 = findViewById<TextView>(R.id.txtExpression2)

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
            runTheGame(showExpression1,1)
            runTheGame(showExpression2,2)

//            firstTerm1 = generateFirstNumber().toString()
//            firstTerm2 = generateFirstNumber().toString()
//            termCount1 = generateNumberOfTerms()
//            termCount2 = generateNumberOfTerms()
//            generateExpression(firstTerm1!!,showExpression1, termCount1!!, firstTerm1!!.toInt(),1)
//            generateExpression(firstTerm2!!,showExpression2, termCount2!!, firstTerm2!!.toInt(),2)

        }

        btnEqual.setOnClickListener {

            runTheGame(showExpression1,1)
            runTheGame(showExpression2,2)

//            firstTerm1 = generateFirstNumber().toString()
//            firstTerm2 = generateFirstNumber().toString()
//            termCount1 = generateNumberOfTerms()
//            termCount2 = generateNumberOfTerms()
//            generateExpression(firstTerm1!!,showExpression1, termCount1!!, firstTerm1!!.toInt(),1)
//            generateExpression(firstTerm2!!,showExpression2, termCount2!!, firstTerm2!!.toInt(),2)

        }

        btnLesser.setOnClickListener {

            runTheGame(showExpression1,1)
            runTheGame(showExpression2,2)

//            firstTerm1 = generateFirstNumber().toString()
//            firstTerm2 = generateFirstNumber().toString()
//            termCount1 = generateNumberOfTerms()
//            termCount2 = generateNumberOfTerms()
//            generateExpression(firstTerm1!!,showExpression1, termCount1!!, firstTerm1!!.toInt(),1)
//            generateExpression(firstTerm2!!,showExpression2, termCount2!!, firstTerm2!!.toInt(),2)

        }
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
        var secondTerm = randomOneToTwenty()
        var expressionNo = expressionNumber
        var expressions: String? = null

        when (operator) {
            0 -> {
                finalValue += secondTerm
            }
            1 -> {
                finalValue -= secondTerm
            }
            2 -> {
                finalValue *= secondTerm
            }
            else -> {
                finalValue /= secondTerm
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

        Log.d("expo 1 ", "$expression1")
        Log.d("expo 2 ", "$expression2")


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