package com.example.computationsskills

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val showCorrect = findViewById<TextView>(R.id.correct)
        val showIncorrect = findViewById<TextView>(R.id.incorrect)

        var correctCount = intent.getIntExtra("correct",0)
        var incorrectCount = intent.getIntExtra("incorrect",0)


        showCorrect.text = "Number of correct selections: $correctCount"
        showCorrect.setTextColor(Color.GREEN)

        showIncorrect.text = "Number of correct selections: $incorrectCount"
        showIncorrect.setTextColor(Color.RED)

    }

    override fun onBackPressed() {
        val backToMain = Intent(this, HomeActivity::class.java)
        startActivity(backToMain)
    }
}