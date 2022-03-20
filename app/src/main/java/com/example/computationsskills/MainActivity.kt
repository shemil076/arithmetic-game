package com.example.computationsskills

import android.annotation.SuppressLint
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

    /**
     *
     * calls this method when the activity is first created
     * initialise the elements
     * @param savedInstanceState Bundle of saved instance state
     */
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialise ui elements
        val btnNewGame = findViewById<Button>(R.id.newGame)
        val btnAbout = findViewById<Button>(R.id.about)

        val image1 = findViewById<ImageView>(R.id.imageView1)
        val image2 = findViewById<ImageView>(R.id.imageView2)
        val image3 = findViewById<ImageView>(R.id.imageView3)
        val image4 = findViewById<ImageView>(R.id.imageView4)
        val image5 = findViewById<ImageView>(R.id.imageView5)
        val buttonlayer = findViewById<LinearLayout>(R.id.linearLayout)


        // initialise the anims
        val leftToRight = AnimationUtils.loadAnimation(this, R.anim.lefttoright)
        val righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft)
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
        val topbottum = AnimationUtils.loadAnimation(this, R.anim.topbottum)

        // add animations on ui elements
        buttonlayer.startAnimation(shake)

        image1.startAnimation(righttoleft)
        image2.startAnimation(leftToRight)
        image3.startAnimation(righttoleft)
        image4.startAnimation(leftToRight)
        image5.startAnimation(topbottum)

        /**
         * navigate to the GameActivity
         */
        btnNewGame.setOnClickListener {             // used an Explicit intent
            val gameActivityIntent = Intent(this,GameActivity::class.java)
            startActivity(gameActivityIntent)
        }

        /**
         * open the PopupWindow when the button pressed.
         */
        btnAbout.setOnClickListener{
            val window = PopupWindow(this)
            window.isFocusable
            val view = layoutInflater.inflate(R.layout.aboutpopup,null)
            window.contentView = view
            window.showAtLocation(view, Gravity.CENTER,0,0)        // location of the popup window

            /**
             * terminate the popup window when press on the popup window
             */
            view.setOnClickListener {
                window.dismiss()
            }
        }

    }


}