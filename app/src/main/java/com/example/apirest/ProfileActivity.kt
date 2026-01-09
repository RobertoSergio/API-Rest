package com.example.apirest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val authManager = AuthManager(this)
        val user = authManager.getCurrentUser()

        val txtUserName = findViewById<TextView>(R.id.txtUser)

        user?.let {
            txtUserName.text = it.name
        }

        val btnLogout = Button(this)
        btnLogout.text = getString(R.string.logout)
        btnLogout.setOnClickListener {
            authManager.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val layout = findViewById<androidx.cardview.widget.CardView>(R.id.cardView)
        val innerLayout = layout.getChildAt(0) as? android.widget.LinearLayout
        innerLayout?.addView(btnLogout)
    }
}