package com.example.computationsskills

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AboutActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        var txtUowNumber = findViewById<TextView>(R.id.txtUowNumber)
        var txtIITNumber = findViewById<TextView>(R.id.txtIITNumber)
        var txtName = findViewById<TextView>(R.id.txtName)

        txtUowNumber.text = "1836048"
        txtIITNumber.text = "20200612"
        txtName. text = "Pramuditha Karunarathna"

    }
}