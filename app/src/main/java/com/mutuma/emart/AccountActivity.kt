package com.mutuma.emart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AccountActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var textViewUsername: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var buttonSignOut: Button
    private lateinit var imageViewProfilePic: ImageView
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        BottomNavigationHandler.setupBottomNavigation(this, bottomNavigationView)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId.orEmpty())

        readUserDetails()
        signOutListener()
        imageProfilePicListener()
        loadProfileImage()
    }

    private fun readUserDetails() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    displayUserDetails(user)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AccountActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayUserDetails(user: User?) {
        textViewEmail = findViewById(R.id.textViewEmail)
        textViewPhone = findViewById(R.id.textPhone)
        textViewUsername = findViewById(R.id.textViewUsername)
        user?.let {
            textViewUsername.text = "Username: ${it.username}"
            textViewEmail.text = "Email: ${it.email}"
            textViewPhone.text = "Phone: ${it.phone}"

        }
    }

    private fun signOutListener()
    {
        buttonSignOut = findViewById(R.id.buttonSignOut)
        buttonSignOut.setOnClickListener {
            signOut()
        }
    }

    private fun signOut()
    {
       auth.signOut()
        startActivity(Intent(this@AccountActivity, LoginActivity::class.java))
        finish()
    }

    private fun imageProfilePicListener(){
        imageViewProfilePic = findViewById(R.id.imageViewProfilePic)
        imageViewProfilePic.setOnClickListener {
            openGalleryForImage()
        }
    }

    private fun saveProfileImage(imageUri: Uri?) {
        if (imageUri != null) {
            val editor = sharedPreferences.edit()
            editor.putString("profileImageUri", imageUri.toString())
            editor.apply()
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getContent.launch(intent)
    }

    private fun loadProfileImage() {
        val savedImageUriString = sharedPreferences.getString("profileImageUri", null)
        val defaultImageUri = Uri.parse("android.resource://$packageName/drawable/ic_account")

        val imageUri = if (savedImageUriString != null) {
            Uri.parse(savedImageUriString)
        } else {
            defaultImageUri
        }
        Glide.with(this)
            .load(imageUri)
            .into(imageViewProfilePic)
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri = data?.data
            Glide.with(this)
                .load(selectedImageUri)
                .into(imageViewProfilePic)
            saveProfileImage(selectedImageUri)
        }

}
}

