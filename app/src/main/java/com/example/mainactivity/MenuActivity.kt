package com.example.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import com.example.mainactivity.databinding.ActivityBmiBinding
import com.example.mainactivity.databinding.ActivityMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutbtn.setOnClickListener{
            auth.signOut()
            val intent = Intent(this,Signin_Activity::class.java)
            startActivity(intent)

        }
        binding.btnBmi.setOnClickListener{
            val intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
        }
        binding.btnStepCounter.setOnClickListener{
            val intent = Intent(this, StepcounterActivity::class.java)
            startActivity(intent)

        }
        binding.btnDietPlan.setOnClickListener{
            val intent = Intent(this, calcounterActivity::class.java)
            startActivity(intent)

        }
        binding.btnHomeworkout.setOnClickListener{
            val intent = Intent(this, WorkoutActivity::class.java)
            startActivity(intent)
        }

    }
}