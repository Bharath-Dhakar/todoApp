package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Get references to views
        val registerButton = findViewById<Button>(R.id.registerButton)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val userNameEditText = findViewById<EditText>(R.id.userName)

        // Register button click listener
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val username = userNameEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                registerUser(email, password, username)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Register user with email and password
    private fun registerUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("RegisterActivity", "createUserWithEmail:success")
                    val user = auth.currentUser
                    user?.let {
                        saveUserToFirestore(it, username)
                    }
                } else {
                    Log.w("RegisterActivity", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Save the user's data (like username) to Firestore
    private fun saveUserToFirestore(user: FirebaseUser, username: String) {
        val userId = user.uid
        val userData = hashMapOf(
            "userId" to userId,
            "username" to username,
            "email" to user.email
        )

        val addOnFailureListener = firestore.collection("users").document(userId)
            .set(userData)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "User profile created for $userId")
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                // Redirect to MainActivity or LoginActivity after successful registration
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }.addOnFailureListener { e ->
                Log.w("RegisterActivity", "Error adding document", e)
                Toast.makeText(this, "Error saving user info. Try again.", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}
