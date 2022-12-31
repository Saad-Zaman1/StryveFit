package com.example.mainactivity

import android.animation.ObjectAnimator
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.adapterRecycleView.CustomAdapter
import com.example.mainactivity.adapterRecycleView.Userclass
import com.example.mainactivity.databinding.ActivityCalcounterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*


class calcounterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalcounterBinding
    private lateinit var recyclerview: RecyclerView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalcounterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseFirestore.getInstance()

        //Recycler view
        recyclerview = findViewById<RecyclerView>(R.id.userRecycleview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
        binding.showdatabtn.setOnClickListener {
            readData()
        }

        val mCountry = arrayOf(
            "       Apple                                        52 ",
            "       Banana                                       96 ",
            "       Orange                                       37 ",
            "       Guava                                        32 ",
            "       Mango                                        42 ",
            "       Peach                                        40 ",
            "       Watermelon                                   20 ",
            "       Strawberry                                   25 ",
            "       Cherries                                     60 ",
            "       Grapes                                       60 ",
            "       Bread                                        45 ",
            "       Puri                                         75 ",
            "       Roti                                         100 ",
            "       Paratha                                      170 ",
            "       Naan                                         262 ",
            "       Green Tea                                    10 ",
            "       Milk Tea                                     45 ",
            "       Cofee                                        45 ",
            "       Plain Milk                                   60 ",
            "       Juice                                        120 ",
        )
        //Dialog of food calories
        binding.mealCal.setOnClickListener {
            val mAlertDialogBuilder = AlertDialog.Builder(this)
            // Row layout is inflated and added to ListView
            val mRowList = layoutInflater.inflate(R.layout.dialog_food_calories, null)
            val mListView = mRowList.findViewById<ListView>(R.id.userlist)

            // Adapter is created and applied to ListView
            val mAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mCountry)
            mListView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
            // Row item is set as view in the Builder and the
            // ListView is displayed in the Alert Dialog
            mAlertDialogBuilder.setView(mRowList)
            val dialog = mAlertDialogBuilder.create()

            dialog.show()
        }

        binding.ChangeGBtn.setOnClickListener {
            val v = if (binding.ChangeGET.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.ChangeGET.visibility = v
            val i = if (binding.SelectGoal.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.SelectGoal.visibility = i
        }

        binding.SelectGoal.setOnClickListener {
            val v = binding.ChangeGET.text.toString()
            val k = if (binding.ChangeGET.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.ChangeGET.visibility = k
            val i = if (binding.SelectGoal.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.SelectGoal.visibility = i
            binding.goalTv.text = v
            binding.progressBar.max = v.toInt()
        }

        val currentProgress = 700
        ObjectAnimator.ofInt(binding.progressBar, "progress", currentProgress).setDuration(2000)
            .start()
        binding.SelectMeal.setOnClickListener {
            firebaseAuth = FirebaseAuth.getInstance()
            val userId = firebaseAuth.currentUser!!.uid
            val food = binding.FoodEt.text.toString()
            val calories = binding.CalTv.text.toString()
            if (food != "" && calories != "") {
                if (firebaseAuth.currentUser != null) {
                    val add = hashMapOf(
                        "ID" to userId,
                        "food" to food,
                        "calories" to calories
                    )
                    dbRef.collection("usersCalories").document()
                        .set(add)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Data added", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Data not added", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "You are not logged in", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Input invalid", Toast.LENGTH_SHORT).show()
            }
            binding.FoodEt.text.clear()
            binding.CalTv.text.clear()
        }
    }
    private fun readData() {
        val data = ArrayList<Userclass>()
        dbRef = FirebaseFirestore.getInstance()
        var userId = firebaseAuth.currentUser!!.uid
        dbRef.collection("usersCalories")
            .whereEqualTo("ID", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "Your data: => ${document.data}")
                    data.add(document.toObject(Userclass::class.java))
                    val adapter = CustomAdapter(data)
                    // Setting the Adapter with the recyclerview
                    recyclerview.adapter = adapter
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}




