package com.example.rickyandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rickyandmorty.util.constant

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}