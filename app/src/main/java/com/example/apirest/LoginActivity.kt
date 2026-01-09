package com.example.apirest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var layoutName: TextInputLayout
    private lateinit var layoutEmail: TextInputLayout
    private lateinit var layoutPassword: TextInputLayout
    private lateinit var layoutConfirmPassword: TextInputLayout
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editConfirmPassword: EditText
    private lateinit var btnAction: Button
    private lateinit var btnToggleMode: Button

    private lateinit var authManager: AuthManager
    private var isLoginMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authManager = AuthManager(this)

        if (authManager.isLoggedIn()) {
            navigateToMain()
            return
        }

        initViews()
        setupListeners()
        updateUIForMode()
    }

    private fun initViews() {
        layoutName = findViewById(R.id.layoutName)
        layoutEmail = findViewById(R.id.layoutEmail)
        layoutPassword = findViewById(R.id.layoutPassword)
        layoutConfirmPassword = findViewById(R.id.layoutConfirmPassword)
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        editConfirmPassword = findViewById(R.id.editConfirmPassword)
        btnAction = findViewById(R.id.btnAction)
        btnToggleMode = findViewById(R.id.btnToggleMode)
    }

    private fun setupListeners() {
        btnToggleMode.setOnClickListener {
            isLoginMode = !isLoginMode
            updateUIForMode()
            clearErrors()
        }

        btnAction.setOnClickListener {
            if (isLoginMode) {
                performLogin()
            } else {
                performRegister()
            }
        }
    }

    private fun updateUIForMode() {
        if (isLoginMode) {
            btnAction.text = getString(R.string.login_title)
            btnToggleMode.text = getString(R.string.no_account)
            layoutName.visibility = android.view.View.GONE
            layoutConfirmPassword.visibility = android.view.View.GONE
        } else {
            btnAction.text = getString(R.string.register_title)
            btnToggleMode.text = getString(R.string.have_account)
            layoutName.visibility = android.view.View.VISIBLE
            layoutConfirmPassword.visibility = android.view.View.VISIBLE
        }
    }

    private fun performLogin() {
        val email = editEmail.text.toString().trim()
        val password = editPassword.text.toString().trim()

        if (validateLogin(email, password)) {
            if (authManager.login(email, password)) {
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                navigateToMain()
            } else {
                layoutEmail.error = getString(R.string.invalid_credentials)
                layoutPassword.error = getString(R.string.invalid_credentials)
            }
        }
    }

    private fun performRegister() {
        val name = editName.text.toString().trim()
        val email = editEmail.text.toString().trim()
        val password = editPassword.text.toString().trim()
        val confirmPassword = editConfirmPassword.text.toString().trim()

        if (validateRegister(name, email, password, confirmPassword)) {
            if (authManager.register(name, email, password)) {
                Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
                navigateToMain()
            } else {
                layoutEmail.error = "E-mail já cadastrado"
            }
        }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        var isValid = true
        clearErrors()

        if (email.isEmpty()) {
            layoutEmail.error = getString(R.string.field_required)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layoutEmail.error = getString(R.string.invalid_email)
            isValid = false
        }

        if (password.isEmpty()) {
            layoutPassword.error = getString(R.string.field_required)
            isValid = false
        } else if (password.length < 6) {
            layoutPassword.error = "Senha deve ter no mínimo 6 caracteres"
            isValid = false
        }

        return isValid
    }

    private fun validateRegister(name: String, email: String, password: String, confirmPassword: String): Boolean {
        var isValid = true
        clearErrors()

        if (name.isEmpty()) {
            layoutName.error = getString(R.string.field_required)
            isValid = false
        }

        if (email.isEmpty()) {
            layoutEmail.error = getString(R.string.field_required)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layoutEmail.error = getString(R.string.invalid_email)
            isValid = false
        }

        if (password.isEmpty()) {
            layoutPassword.error = getString(R.string.field_required)
            isValid = false
        } else if (password.length < 6) {
            layoutPassword.error = "Senha deve ter no mínimo 6 caracteres"
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            layoutConfirmPassword.error = getString(R.string.field_required)
            isValid = false
        } else if (password != confirmPassword) {
            layoutConfirmPassword.error = getString(R.string.password_mismatch)
            isValid = false
        }

        return isValid
    }

    private fun clearErrors() {
        layoutName.error = null
        layoutEmail.error = null
        layoutPassword.error = null
        layoutConfirmPassword.error = null
    }

    private fun navigateToMain() {
        startActivity(Intent(this, PokemonListActivity::class.java))
        finish()
    }
}