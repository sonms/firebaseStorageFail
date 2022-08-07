package com.example.firebase_storage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn : Button = findViewById(R.id.get_image)
        btn.setOnClickListener {
            val intent = Intent(this, StorageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}