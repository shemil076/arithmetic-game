package com.example.computationsskills

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView



class ScoreActivity : AppCompatActivity() {


    /**
     *
     * calls this method when the activity is first created
     * initialise the elements
     * @param savedInstanceState Bundle of saved instance state
     */
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

//        initialise the views
        val showCorrect = findViewById<TextView>(R.id.correct)
        val showIncorrect = findViewById<TextView>(R.id.incorrect)

        val image1 = findViewById<ImageView>(R.id.imageView1)
        val image2 = findViewById<ImageView>(R.id.imageView2)
        val image3 = findViewById<ImageView>(R.id.imageView3)
        val image4 = findViewById<ImageView>(R.id.imageView4)
        val image5 = findViewById<ImageView>(R.id.imageView5)

//        initialise the anim
        val leftToRight = AnimationUtils.loadAnimation(this, R.anim.lefttoright)
        val upDown = AnimationUtils.loadAnimation(this, R.anim.updown)
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
        val shakeMore = AnimationUtils.loadAnimation(this, R.anim.shakemore)
        val starRotate = AnimationUtils.loadAnimation(this, R.anim.starrotate)

//        add anims to the views
        image1.startAnimation(shake)
        image2.startAnimation(shakeMore)
        image3.startAnimation(leftToRight)
        image4.startAnimation(starRotate)
        image5.startAnimation(upDown)


//       assign the values from the intent to the respective variables
        val correctCount = intent.getIntExtra("correct",0)
        val incorrectCount = intent.getIntExtra("incorrect",0)



        showCorrect.text = "Number of correct selections: $correctCount"
        showIncorrect.text = "Number of incorrect selections: $incorrectCount"

        showCorrect.setTextColor(Color.GREEN)
        showIncorrect.setTextColor(Color.RED)
    }

    /**
     * Redirected to the MainActivity/HomePage when the user presses back navigation button
     * Stop reaching to the previous page when pressing the back navigation button
     */
    override fun onBackPressed() {
        val backToMain = Intent(this, MainActivity::class.java)
        startActivity(backToMain)
    }
}