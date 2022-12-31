package com.example.mainactivity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mainactivity.databinding.ActivitySignUpBinding
import com.example.mainactivity.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Signin_Activity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            login();
        }
        binding.gotosignup.setOnClickListener {
            val intent = Intent(this, SignUp_Activity::class.java)
            startActivity(intent);
        }
        binding.resetpass.setOnClickListener {
            resetPass()
        }
    }

    private fun login() {

        val email = binding.etEmailLogin.text.toString()
        val pass = binding.etPassLogin.text.toString()
        if (email.isNotEmpty() && pass.isNotEmpty()) {

            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetPass() {
        val email = binding.etEmailLogin.text.toString()
        if (email.isNotEmpty()) {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Reset email send", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Email address is not correct", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "PLease enter email address to reset password", Toast.LENGTH_SHORT)
                .show()

        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}



