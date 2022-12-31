package com.example.mainactivity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mainactivity.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp_Activity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.btnSignup.setOnClickListener {
            val name = binding.etNameSignup.text.toString()
            val email = binding.etEmailSignup.text.toString()
            val pass = binding.etPassSignup.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()) {

                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {

                        val intent = Intent(this, Signin_Activity::class.java)
                        startActivity(intent);

                        val userid = FirebaseAuth.getInstance().currentUser!!.uid;
                        val add = hashMapOf(
                            "ID" to userid,
                            "name" to name,
                            "email" to email
                        )
                        // !! means (not null)
                        db.collection("users")
                            .document(userid)
                            .set(add)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Data added", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            .addOnFailureListener{
                                Toast.makeText(this, "Data not added", Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            } else {

                Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
        binding.gotologin.setOnClickListener {
            val intent = Intent(this, Signin_Activity::class.java)
            startActivity(intent);
        }
    }
}