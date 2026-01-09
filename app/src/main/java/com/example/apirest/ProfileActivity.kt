package com.example.apirest

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val prefs: SharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val user = prefs.getString("username", "")

        findViewById<TextView>(R.id.txtUser).text = user
    }
}
