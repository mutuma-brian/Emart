package com.mutuma.emart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var registerTextView: TextView
    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText : EditText
    private lateinit var passwordEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginListener()
        registerListener()

    }

    private fun loginListener() {
        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            login()
        }
    }

    private fun registerListener() {
        registerTextView = findViewById(R.id.noAccountTextView)
        registerTextView.setOnClickListener {
            register()
        }
    }

    private fun login() {
        auth = FirebaseAuth.getInstance()
        emailEditText = findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        // sign in fails, display a message to the user.
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
        }

    }

    private fun register() {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        finish()
    }

}

