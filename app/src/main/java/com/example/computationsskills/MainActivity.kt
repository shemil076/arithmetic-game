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

        // initialise the views
        val btnNewGame = findViewById<Button>(R.id.newGame)
        val btnAbout = findViewById<Button>(R.id.about)

        val image1 = findViewById<ImageView>(R.id.imageView1)
        val image2 = findViewById<ImageView>(R.id.imageView2)
        val image3 = findViewById<ImageView>(R.id.imageView3)
        val image4 = findViewById<ImageView>(R.id.imageView4)
        val image5 = findViewById<ImageView>(R.id.imageView5)
        val buttonLayer = findViewById<LinearLayout>(R.id.linearLayout)


        // initialise the anims
        val leftToRight = AnimationUtils.loadAnimation(this, R.anim.lefttoright)
        val rightToLeft = AnimationUtils.loadAnimation(this, R.anim.righttoleft)
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
        val topBottom = AnimationUtils.loadAnimation(this, R.anim.topbottum)

        // add animations on views
        buttonLayer.startAnimation(shake)

        image1.startAnimation(rightToLeft)
        image2.startAnimation(leftToRight)
        image3.startAnimation(rightToLeft)
        image4.startAnimation(leftToRight)
        image5.startAnimation(topBottom)


        /**\
         * remove the action bar (app bar)              reference: https://www.geeksforgeeks.org/different-ways-to-hide-action-bar-in-android-with-examples/
         */
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }

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