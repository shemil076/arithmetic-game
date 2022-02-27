package com.example.computationsskills

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnNewGame = findViewById<Button>(R.id.newGame)
        var btnAbout = findViewById<Button>(R.id.about)


        btnNewGame.setOnClickListener {
            val gameActivityIntent = Intent(this,GameActivity::class.java)
            startActivity(gameActivityIntent)
        }
        btnAbout.setOnClickListener {
            val aboutActivityIntent = Intent(this, AboutActivity::class.java)
            startActivity(aboutActivityIntent)
        }

    }
}