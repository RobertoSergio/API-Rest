package com.example.apirest

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val prefs: SharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val savedUser = prefs.getString("username", null)

        if (savedUser != null) {
            startActivity(Intent(this, PokemonListActivity::class.java))
            finish()
        }

        val input = findViewById<EditText>(R.id.editUsername)
        val button = findViewById<Button>(R.id.btnLogin)

        button.setOnClickListener {
            prefs.edit().putString("username", input.text.toString()).apply()
            startActivity(Intent(this, PokemonListActivity::class.java))
            finish()
        }
    }
}
