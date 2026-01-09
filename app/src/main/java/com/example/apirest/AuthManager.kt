package com.example.apirest

import android.content.Context
import android.content.SharedPreferences
import com.example.apirest.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AuthManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val usersKey = "users"
    private val currentUserKey = "current_user"

    fun register(name: String, email: String, password: String): Boolean {
        if (getUserByEmail(email) != null) {
            return false
        }

        val users = getUsers()
        val newId = users.size + 1

        val newUser = User(newId, name, email, password)
        users.add(newUser)

        saveUsers(users)
        setCurrentUser(newUser)

        return true
    }

    fun login(email: String, password: String): Boolean {
        val user = getUserByEmail(email)

        return if (user != null && user.password == password) {
            setCurrentUser(user)
            true
        } else {
            false
        }
    }

    fun logout() {
        prefs.edit().remove(currentUserKey).apply()
    }

    fun isLoggedIn(): Boolean {
        return getCurrentUser() != null
    }

    fun getCurrentUser(): User? {
        val userJson = prefs.getString(currentUserKey, null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

    private fun setCurrentUser(user: User) {
        val userJson = gson.toJson(user)
        prefs.edit().putString(currentUserKey, userJson).apply()
    }

    private fun getUsers(): MutableList<User> {
        val usersJson = prefs.getString(usersKey, "[]")
        val type = object : TypeToken<MutableList<User>>() {}.type
        return gson.fromJson(usersJson, type)
    }

    private fun saveUsers(users: List<User>) {
        val usersJson = gson.toJson(users)
        prefs.edit().putString(usersKey, usersJson).apply()
    }

    private fun getUserByEmail(email: String): User? {
        return getUsers().find { it.email == email }
    }
}