package com.mutuma.emart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var registerButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()

        registerListener()
    }

    private fun registerListener (){
        registerButton = findViewById(R.id.buttonSignUp)
        emailEditText = findViewById(R.id.editTextEmail)
        phoneEditText = findViewById(R.id.editTextPhone)
        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if(email.isNotEmpty() && password.isNotEmpty() && phone.isNotEmpty() && username.isNotEmpty() && confirmPassword.isNotEmpty())
            {
                if(password == confirmPassword)
                {
                    registerThisUser(email, password, phone, username)
                }
                else
                {
                    Toast.makeText(this, "Passwords Don't Match", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerThisUser(email : String,password: String,phone: String,username: String)
    {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val userId = auth.currentUser?.uid ?: ""
                    val user = User(email, phone, username)

                    database.reference.child("users").child(userId).setValue(user)
                    Toast.makeText(this@RegisterActivity, "Registered Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }
                else
                {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                }

            }
    }
}
