package com.example.computationsskills

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.PopupWindow

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


        btnAbout.setOnClickListener{
            val window = PopupWindow(this)
            window.isFocusable
            val view = layoutInflater.inflate(R.layout.aboutpopup,null)
            window.contentView = view
            window.showAtLocation(view, Gravity.CENTER,0,0)
            view.setOnClickListener {
                window.dismiss()
            }
        }





    }
}