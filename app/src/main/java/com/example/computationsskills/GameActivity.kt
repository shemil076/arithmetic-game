package com.example.computationsskills

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*
import kotlin.concurrent.schedule

class GameActivity : AppCompatActivity() {


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
    private var correctCount: Int = 0                              // number of expressions that correctly answered
    private var inCorrectCount: Int = 0                            // number of expressions that incorrectly answered
    var timerCorrectCount: Int = 0                         // variable that use to count 5 correct answers
    var seconds =   20000                                      // number of seconds that show in timer



    lateinit var showExpression1: TextView                 // TextView that shows the first expression
    private lateinit var showExpression2: TextView                 // TextView that shows the second expression
    lateinit var showTimer : TextView                      // TextView that shows the timer
    lateinit var timer: Timer                               // timer object

    /**
     *
     * calls this method when the activity is first created
     * initialise the elements
     * @param savedInstanceState Bundle of saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // initialise the views
        val btnGreater = findViewById<Button>(R.id.btnGreatrer)
        val btnEqual = findViewById<Button>(R.id.btnEqual)
        val btnLesser = findViewById<Button>(R.id.btnLess)
        showExpression1 = findViewById(R.id.txtExpression1)
        showExpression2 = findViewById(R.id.txtExpression2)
        val textResult = findViewById<TextView>(R.id.txtResult)
        showTimer = findViewById(R.id.timer)




        runTheGame(showExpression1, 1)       // calls to generate the first expression
        runTheGame(showExpression2, 2)       // calls to generate the second expression
        timer()

        /**
         * check the user input (button pressed) is correct.
         * and recall the methods that generate the expression
         */
        btnGreater.setOnClickListener {
            check(checkOptions[0], textResult)          // button represent left expression is greater than right
            runTheGame(showExpression1, 1)
            runTheGame(showExpression2, 2)
        }

        btnEqual.setOnClickListener {                   // button represent left expression is equal than right
            check(checkOptions[1], textResult)
            runTheGame(showExpression1, 1)
            runTheGame(showExpression2, 2)
        }

        btnLesser.setOnClickListener {                   // button represent left expression is less than right
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

        // set the values to a bundle
        outState.putInt("correct", correctCount)
        outState.putInt("incorrect", inCorrectCount)
        outState.putInt("timerCorrectCount", timerCorrectCount)
        outState.putInt("seconds", seconds)
        outState.putInt("value1", valueOfExpression1)
        outState.putInt("value2", valueOfExpression2)
        outState.putString("expression1", expression1)
        outState.putString("expression2", expression2)
        timer.cancel()      // stops the timer



    }

    /**
     * use to restore the savedInstanceState in order to handle configuration changes
     *
     * @param savedInstanceState saved Instance State
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // reassign the values to the respective varables
        correctCount = savedInstanceState.getInt("correct", 0)
        inCorrectCount = savedInstanceState.getInt("incorrect", 0)
        timerCorrectCount = savedInstanceState.getInt("timerCorrectCount", 0)
        seconds = savedInstanceState.getInt("seconds",50000)
        valueOfExpression1 = savedInstanceState.getInt("value1", 0)
        valueOfExpression2 = savedInstanceState.getInt("value2", 0)
        expression1 = savedInstanceState.getString("expression1", null)
        expression2 = savedInstanceState.getString("expression2", null)

        // set the expression1 and expression2 in order to make sure to not to change when in rotation
        showExpression1.text = expression1
        showExpression2.text = expression2
    }

    /**
     * calls the method that generate the expression according to the argument
     *
     * @param showExpression    TextView that shows the expression
     * @param expressionNumber  Expression number whether expression 1 or 2
     */
    private fun runTheGame(showExpression: TextView, expressionNumber: Int) {
        when (expressionNumber) {
            1 -> { // generate the first expression
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
            2 -> {  // generate the second expression
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
    private fun generateExpression(
        firstTerm1: String,
        showExpression: TextView,
        termCount1: Int,
        final: Int,
        expressionNumber: Int
    ) {
        var finalValue = final          // initially the final value is equal to the first term
        var termCount = termCount1
        val operator = randomUpToFour()
        var expressions: String? = null
        var secondTerm: Int

        while (true) {
            secondTerm = randomOneToTwenty()
            when (operator) {
                0 -> { // when the operator is "+"
                    if ((finalValue + secondTerm) <= 100) { // to make sure the total is no more than 100
                        finalValue += secondTerm
                        break
                    } else {
                        continue
                    }

                }
                1 -> { // when the operator is "-"
                    if ((finalValue - secondTerm) <= 100) { // to make sure the summation is no more than 100
                        finalValue -= secondTerm
                        break
                    } else {
                        continue
                    }

                }
                2 -> {      //  when the operator is "*"
                    if ((finalValue * secondTerm) <= 100) {  // to make sure the multiplication is no more than 100
                        finalValue *= secondTerm
                        break
                    } else {
                        continue
                    }
                }
                3 -> {  // when the operator is "/"
                    if ((finalValue % secondTerm == 0) && ((finalValue / secondTerm) <= 100)) {  // to make sure the result is a whole number and also to make sure the result is no more than 100
                        finalValue /= secondTerm
                        break
                    } else {
                        continue
                    }
                }
            }
        }
        when (expressionNumber) {
            1 -> { // for first Expression
                expression1 = firstTerm1 + operators[operator] + secondTerm
                expressions = expression1
            }
            2 -> { // for second expression
                expression2 = firstTerm1 + operators[operator] + secondTerm
                expressions = expression2
            }
        }


        termCount -= 1

        // set the other terms according to the number of terms
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



    /**
     * Timer that ticks on per second.
     * Increase the timer by 10 seconds when  correctCount % 5 == 0
     *
     */
    private fun timer(){
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                seconds -= 1000

                runOnUiThread {
                    if (timerCorrectCount == 5) { // reminder : change 2 to 5 -> 2 is used in debug purpose
                        timerCorrectCount = 0
                        seconds += 10000
                    }

                    if (seconds <= 0) {
                        timer.cancel()
                        showScore()
                    }

                    when {
                        seconds > 30000 -> {
                            showTimer.setTextColor(Color.GREEN)
                        }
                        seconds > 20000 -> {
                            showTimer.setTextColor(Color.YELLOW)
                            showTimer.textSize = 20F
                        }
                        else -> {
                            showTimer.setTextColor(Color.RED)
                            showTimer.textSize = 21F
                        }
                    }


                    val min = (seconds / 1000) / 60
                    val sec = (seconds / 1000) % 60
                    showTimer.text = "Seconds remaining\n$min : $sec"
                    print("$min $sec\n")
                }
            }
        },0,1000)
    }


    /**
     * moves to the score activity
     *
     */
    fun showScore() {
        val scoreActivityIntent = Intent(this, ScoreActivity::class.java)
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
    private fun randomUpToFour(): Int {
        return Random().nextInt(4)
    }

    /**
     * generate a number in range 1 to 3
     *
     * @return generated number
     */
    private fun randomOneToThree(): Int {
        return 1 + Random().nextInt(3)
    }

    /**
     * generate a number in range 1 to 20
     *
     * @return generated number
     */
    private fun randomOneToTwenty(): Int {
        return 1 + Random().nextInt(20)
    }

}