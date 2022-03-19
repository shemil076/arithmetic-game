package com.example.computationsskills

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.*
import java.util.*
import kotlin.concurrent.schedule

class GameActivity : AppCompatActivity() {

    lateinit var preference: SharedPreferences

    //    initialise the global variable
    var operators = listOf("+", "-", "*", "/")                // list of operators in order to use randomly
    var checkOptions = listOf("greater", "equal", "less")
    var termCount1: Int? = 0                               // number of terms in expression 1
    var termCount2: Int? = 0                               // number of terms in expression 2
    var firstTerm1: String? = null                         // first term of the expression 1
    var firstTerm2: String? = null                         // first term of the expression 2
    var valueOfExpression1: Int = 0                         // result after solving the first expression
    var valueOfExpression2: Int = 0                         // result after solving the second expression
    var expression1: String? = null                           // first expression
    var expression2: String? = null                           // second expression
    var timerStartValue = 5000000000                        // number of ticks in order to have a continuous time period
    var correctCount: Int = 0                              // number of expressions that correctly answered
    var inCorrectCount: Int = 0                            // number of expressions that incorrectly answered
    var timerCorrectCount: Int = 0                         // variable that use to count 5 correct answers
    var seconds =   50000                                      // number of seconds that show in timer
    var endTime : Long = 0



    lateinit var showExpression1: TextView                 // TextView that shows the first expression
    lateinit var showExpression2: TextView                 // TextView that shows the second expression
    lateinit var showTimer : TextView
    lateinit var timer: Timer

    /**
     *
     * calls this method when the activity is first created
     * initialise the elements
     * @param savedInstanceState Bundle of saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // initialise the buttons and text fields
        val btnGreater = findViewById<Button>(R.id.btnGreatrer)
        val btnEqual = findViewById<Button>(R.id.btnEqual)
        val btnLesser = findViewById<Button>(R.id.btnLess)
        showExpression1 = findViewById<TextView>(R.id.txtExpression1)
        showExpression2 = findViewById<TextView>(R.id.txtExpression2)
        val textResult = findViewById<TextView>(R.id.txtResult)
        showTimer = findViewById<TextView>(R.id.timer)




        runTheGame(showExpression1, 1)       // calls to generate the first expression
        runTheGame(showExpression2, 2)       // calls to generate the second expression
        timer()

        btnGreater.setOnClickListener {
            check(checkOptions[0], textResult)

            runTheGame(showExpression1, 1)
            runTheGame(showExpression2, 2)
        }

        btnEqual.setOnClickListener {
            check(checkOptions[1], textResult)
            runTheGame(showExpression1, 1)
            runTheGame(showExpression2, 2)
        }

        btnLesser.setOnClickListener {
            check(checkOptions[2], textResult)
            runTheGame(showExpression1, 1)
            runTheGame(showExpression2, 2)
        }
    }

    /**
     * to save the instance state, In order to save the current state even in configuration changes,
     * Used to handle configuration changes (Screen rotation)
     *
     * @param outState Bundle of state that save
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)


        outState.putInt("correct", correctCount)
        outState.putInt("incorrect", inCorrectCount)
        outState.putInt("timerCorrectCount", timerCorrectCount)
        outState.putInt("seconds", seconds)
        outState.putInt("value1", valueOfExpression1)
        outState.putInt("value2", valueOfExpression2)
        outState.putString("expression1", expression1)

        outState.putString("expression2", expression2)
        timer.cancel()


    }

    /**
     * use to restore the savedInstanceState in order to handle configuration changes
     *
     * @param savedInstanceState saved Instance State
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        correctCount = savedInstanceState.getInt("correct", 0)
        inCorrectCount = savedInstanceState.getInt("incorrect", 0)
        timerCorrectCount = savedInstanceState.getInt("timerCorrectCount", 0)
        seconds = savedInstanceState.getInt("seconds",50000)


        valueOfExpression1 = savedInstanceState.getInt("value1", 0)
        valueOfExpression2 = savedInstanceState.getInt("value2", 0)
        expression1 = savedInstanceState.getString("expression1", null)
        expression2 = savedInstanceState.getString("expression2", null)

        showExpression1.text = expression1
        showExpression2.text = expression2
    }

    /**
     * calls the method that generate the expression according to the argument
     *
     * @param showExpression    TextView that shows the expression
     * @param expressionNumber  Expression number whether expression 1 or 2
     */
    fun runTheGame(showExpression: TextView, expressionNumber: Int) {
        when (expressionNumber) {
            1 -> {
                firstTerm1 = randomOneToTwenty().toString()
                termCount1 = randomOneToThree()
                generateExpression(
                    firstTerm1!!,
                    showExpression,
                    termCount1!!,
                    firstTerm1!!.toInt(),
                    expressionNumber
                )
            }
            2 -> {
                firstTerm2 = randomOneToTwenty().toString()
                termCount2 = randomOneToThree()
                generateExpression(
                    firstTerm2!!,
                    showExpression,
                    termCount2!!,
                    firstTerm2!!.toInt(),
                    expressionNumber
                )
            }
        }
    }

    /**
     * Generate the expression randomly according to the arguments
     *
     * @param firstTerm1    first term of the expression    (randomly generated)
     * @param showExpression    Text view
     * @param termCount1 number of terms should be in an expression (randomly generated)
     * @param final      value of the expression after solving (but initially final value is equal to the first term )
     * @param expressionNumber
     */
    fun generateExpression(
        firstTerm1: String,
        showExpression: TextView,
        termCount1: Int,
        final: Int,
        expressionNumber: Int
    ) {
        var finalValue = final
        var termCount = termCount1
        val operator = randomUpToFour()
        var expressions: String? = null
        var secondTerm: Int

        while (true) {
            secondTerm = randomOneToTwenty()
            when (operator) {
                0 -> {
                    if ((finalValue + secondTerm) <= 100) {
                        finalValue += secondTerm
                        break
                    } else {
                        continue
                    }

                }
                1 -> {
                    if ((finalValue - secondTerm) <= 100) {
                        finalValue -= secondTerm
                        break
                    } else {
                        continue
                    }

                }
                2 -> {
                    if ((finalValue * secondTerm) <= 100) {
                        finalValue *= secondTerm
                        break
                    } else {
                        continue
                    }
                }
                3 -> {
                    if ((finalValue % secondTerm == 0) && ((finalValue / secondTerm) <= 100)) {
                        finalValue /= secondTerm
                        break
                    } else {
                        continue
                    }
                }
            }
        }
        when (expressionNumber) {
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

        if (termCount > 0) {
            generateExpression(
                "($expressions)",
                showExpression,
                termCount,
                finalValue,
                expressionNumber
            )

        } else {
            when (expressionNumber) {
                1 -> {
                    valueOfExpression1 = finalValue
                }
                2 -> {
                    valueOfExpression2 = finalValue
                }
            }
            showExpression.text = expressions
        }


//        Log.d("expo 1 ", "$expression1 = $valueOfExpression1")
//        Log.d("expo 2 ", "$expression2 = $valueOfExpression2")

    }

    /**
     * Check whether the user input (button that user chose) is correct
     *
     * @param checkOption   whether answer is greater, less or equal
     * @param txtResult     Text view that shows the result
     */
    @SuppressLint("SetTextI18n")
    fun check(checkOption: String, txtResult: TextView) {
        when (checkOption) {
            "greater" -> {
                if (valueOfExpression1 > valueOfExpression2) {
                    txtResult.text = "CORRECT!"
                    correctCount += 1
                    timerCorrectCount += 1
                    txtResult.setTextColor(Color.GREEN)

                    Timer().schedule(500) {
                        txtResult.text = ""
                    }

                } else {
                    txtResult.text = "WRONG!!"
                    inCorrectCount += 1
                    txtResult.setTextColor(Color.RED)

                    Timer().schedule(500) {
                        txtResult.text = ""
                    }


                }
            }
            "equal" -> {
                if (valueOfExpression1 == valueOfExpression2) {
                    txtResult.text = "CORRECT!"
                    correctCount += 1
                    timerCorrectCount += 1
                    txtResult.setTextColor(Color.GREEN)

                    Timer().schedule(500) {
                        txtResult.text = ""
                    }


                } else {
                    txtResult.text = "WRONG!!"
                    inCorrectCount += 1
                    txtResult.setTextColor(Color.RED)

                    Timer().schedule(500) {
                        txtResult.text = ""
                    }
                }
            }
            "less" -> {
                if (valueOfExpression1 < valueOfExpression2) {
                    txtResult.text = "CORRECT!"
                    correctCount += 1
                    timerCorrectCount += 1
                    txtResult.setTextColor(Color.GREEN)

                    Timer().schedule(500) {
                        txtResult.text = ""
                    }


                } else {
                    txtResult.text = "WRONG!!"
                    inCorrectCount += 1
                    txtResult.setTextColor(Color.RED)

                    Timer().schedule(500) {
                        txtResult.text = ""
                    }

                }
            }
        }
    }

    private fun timerStart() {
        val timer = findViewById<TextView>(R.id.timer)
        object : CountDownTimer(timerStartValue.toLong(), 1000) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {

                if (timerCorrectCount == 2) { // reminder : change 2 to 5 -> 2 is used in debug purpose
                    timerCorrectCount = 0
                    seconds += 10
                }


                if (seconds > 30) {
                    timer.setTextColor(Color.GREEN)
                } else if (seconds > 10) {
                    timer.setTextColor(Color.YELLOW)
                    timer.textSize = 20F
                } else {
                    timer.setTextColor(Color.RED)
                    timer.textSize = 21F
                }

                timer.text = "Seconds remaining\n0 : $seconds"
                Log.d("timer ", seconds.toString())

                if (seconds == 0) {
                    onFinish()
                    cancel()
                }
                seconds--
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

    fun timer(){
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                seconds -= 1000

                runOnUiThread(Runnable() {
                    if (timerCorrectCount == 2) { // reminder : change 2 to 5 -> 2 is used in debug purpose
                        timerCorrectCount = 0
                        seconds += 10000
                    }

                    if(seconds <= 0){
                        timer.cancel()
                        showScore()
                    }

                    if (seconds > 30000) {
                        showTimer.setTextColor(Color.GREEN)
                    } else if (seconds > 20000) {
                        showTimer.setTextColor(Color.YELLOW)
                        showTimer.textSize = 20F
                    } else {
                        showTimer.setTextColor(Color.RED)
                        showTimer.textSize = 21F
                    }


                    var min = (seconds /1000)/60
                    var sec = (seconds /1000)%60
                    showTimer.text = "Seconds remaining\n$min : $sec"
                    print("$min $sec\n")
                })
            }
        },0,1000)
    }


    /**
     * moves to the score activity
     *
     */
    fun showScore() {
        var scoreActivityIntent = Intent(this, ScoreActivity::class.java)
        scoreActivityIntent.putExtra("correct", correctCount)
        scoreActivityIntent.putExtra("incorrect", inCorrectCount)
        Toast.makeText(this, "Moving on...", Toast.LENGTH_SHORT).show()
        startActivity(scoreActivityIntent)


    }

    /**
     * generate a number in range 0 to 4
     *
     * @return generated number
     */
    fun randomUpToFour(): Int {
        return Random().nextInt(4)
    }

    /**
     * generate a number in range 1 to 3
     *
     * @return generated number
     */
    fun randomOneToThree(): Int {
        return 1 + Random().nextInt(3)
    }

    /**
     * generate a number in range 1 to 20
     *
     * @return generated number
     */
    fun randomOneToTwenty(): Int {
        return 1 + Random().nextInt(20)
    }

}