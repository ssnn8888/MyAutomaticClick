package com.example.myautomaticclick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myautomaticclick.util.startDuokan

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        startDuokan(this)
        finish()
    }
}