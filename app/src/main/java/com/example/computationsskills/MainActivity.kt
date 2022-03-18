package com.example.computationsskills

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnNewGame = findViewById<Button>(R.id.newGame)
        var btnAbout = findViewById<Button>(R.id.about)
        val buttonlayer = findViewById<LinearLayout>(R.id.linearLayout)

        val leftToRight = AnimationUtils.loadAnimation(this, R.anim.lefttoright)
        val righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft)
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
        val topbottum = AnimationUtils.loadAnimation(this, R.anim.topbottum)

        buttonlayer.startAnimation(shake)

        val image1 = findViewById<ImageView>(R.id.imageView1)
        val image2 = findViewById<ImageView>(R.id.imageView2)
        val image3 = findViewById<ImageView>(R.id.imageView3)
        val image4 = findViewById<ImageView>(R.id.imageView4)
        val image5 = findViewById<ImageView>(R.id.imageView5)


        image1.startAnimation(righttoleft)
        image2.startAnimation(leftToRight)
        image3.startAnimation(righttoleft)
        image4.startAnimation(leftToRight)
        image5.startAnimation(topbottum)


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