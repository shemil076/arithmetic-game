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
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val showCorrect = findViewById<TextView>(R.id.correct)
        val showIncorrect = findViewById<TextView>(R.id.incorrect)

        val image1 = findViewById<ImageView>(R.id.imageView1)
        val image2 = findViewById<ImageView>(R.id.imageView2)
        val image3 = findViewById<ImageView>(R.id.imageView3)
        val image4 = findViewById<ImageView>(R.id.imageView4)
        val image5 = findViewById<ImageView>(R.id.imageView5)

        val leftToRight = AnimationUtils.loadAnimation(this, R.anim.lefttoright)
        val updown = AnimationUtils.loadAnimation(this, R.anim.updown)
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
        val shakemore = AnimationUtils.loadAnimation(this, R.anim.shakemore)
        val starrotate = AnimationUtils.loadAnimation(this, R.anim.starrotate)

        image1.startAnimation(shake)
        image2.startAnimation(shakemore)
        image3.startAnimation(leftToRight)
        image4.startAnimation(starrotate)
        image5.startAnimation(updown)


        var correctCount = intent.getIntExtra("correct",0)
        var incorrectCount = intent.getIntExtra("incorrect",0)



        showCorrect.text = "Number of correct selections: $correctCount"
        showIncorrect.text = "Number of incorrect selections: $incorrectCount"

        showCorrect.setTextColor(Color.GREEN)
        showIncorrect.setTextColor(Color.RED)
    }

    override fun onBackPressed() {
        val backToMain = Intent(this, MainActivity::class.java)
        startActivity(backToMain)
    }
}