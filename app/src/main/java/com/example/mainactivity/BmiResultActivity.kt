package com.example.mainactivity

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.RED
import android.graphics.drawable.ColorDrawable
import android.hardware.camera2.params.RggbChannelVector.RED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mainactivity.adapterRecycleView.CustomAdapter
import com.example.mainactivity.databinding.ActivityBmiResultBinding
import com.example.mainactivity.databinding.DialogMUnderweight3045Binding

import com.example.mainactivity.databinding.DialogOverwtwThBinding
import com.example.mainactivity.databinding.DialogOverwtwThFemaleBinding
import com.example.mainactivity.databinding.DialogRcrunchesBinding
import com.example.mainactivity.databinding.DialogUnderwthFortyFemaleBinding
import com.example.mainactivity.databinding.DialogUnderwtwThBinding
import com.example.mainactivity.databinding.DialogUnderwtwThMaleBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FirebaseFirestore

class BmiResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiResultBinding
    private lateinit var bindingDialog: DialogUnderwtwThBinding
    private lateinit var bindingDialogMU: DialogUnderwtwThMaleBinding
    private lateinit var bindingDialogFU: DialogUnderwthFortyFemaleBinding
    private lateinit var bindingDialogMO: DialogOverwtwThBinding
    private lateinit var bindingDialogFO: DialogOverwtwThFemaleBinding
    private lateinit var bindingDialogMaleU: DialogMUnderweight3045Binding

    private lateinit var dbRef: FirebaseFirestore

    var mbmi: String = "0"
    var intbmi: Float = 0f
    var weight: String = "0"
    var height: String = "0"
    var intheight: Float = 0f
    var intweight: Float = 0f
    var age: String = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.elevation = 0f
        supportActionBar?.setTitle(Html.fromHtml("font color=\"white\"></font>"))
        supportActionBar?.title = "Result"

  val intent: Intent = getIntent()
        height = intent.getStringExtra("height").toString()
        weight = intent.getStringExtra("weight").toString()


        intheight = height.toFloat()
        intweight = weight.toFloat()

        intheight = intheight / 100

        intbmi = intweight / (intheight * intheight)


        if (intbmi < 16) {
            binding.bmicategorydispaly.text = "Severe Thinness"

            binding.bmicategorydispaly.setTextColor(Color.RED)

        } else if (intbmi < 16.9 && intbmi > 16) {
            binding.bmicategorydispaly.text = "Moderate Thinness"
            binding.bmicategorydispaly.setTextColor(Color.RED)

        } else if (intbmi < 18.4 && intbmi > 17) {
            binding.bmicategorydispaly.text = "Mild Thinness"

        } else if (intbmi < 25 && intbmi > 18.4) {
            binding.bmicategorydispaly.text = "Normal"

        } else if (intbmi < 29.4 && intbmi > 25) {
            binding.bmicategorydispaly.text = "Overweight"
            binding.bmicategorydispaly.setTextColor(Color.RED)
        } else {
            binding.bmicategorydispaly.text = "Suffering from Obesity"
            binding.bmicategorydispaly.setTextColor(Color.RED)
        }
        binding.genderdisplay.text = intent.getStringExtra("gender");
        age = intent.getStringExtra("age").toString()
        if (binding.bmicategorydispaly.text == "Normal") {
            binding.getmeal.visibility = View.GONE
        }
        if (binding.genderdisplay.text == "Male") {
            binding.imageview.setImageResource(R.drawable.bmimalee)
        } else {
            binding.imageview.setImageResource(R.drawable.bmifemalee)
        }

            //Female over&under weight
        if (age > "20" && age < "30" && binding.genderdisplay.text == "Female") {
            binding.agedisplay.text = "Age Group: 20 - 30"
            if (binding.bmicategorydispaly.text == "Mild Thinness"
                || binding.bmicategorydispaly.text == "Moderate Thinness"
                || binding.bmicategorydispaly.text == "Severe Thinness"
            ) {
                binding.calneeded.visibility = View.VISIBLE
                binding.calneeded.text = "Calories needed: 2100 to 2600"

                binding.getmeal.setOnClickListener {
                    getdietUnderW_tw_th()
                }
            }else if(binding.bmicategorydispaly.text == "Overweight"
                || binding.bmicategorydispaly.text == "Suffering from Obesity"
            ){
                binding.calneeded.visibility = View.VISIBLE
                binding.calneeded.text = "Calories needed: 1600 to 2400"
                    binding.getmeal.setOnClickListener {
                        Toast.makeText(this, "Calories", Toast.LENGTH_SHORT)
                            .show()
                        getdietOverW_tw_th_female()
                    }
            }
        }
        //Male over&underweight  20 - 30
        else if (age > "20" && age < "30" && binding.genderdisplay.text == "Male") {
            binding.agedisplay.text = "Age Group: 20 - 30"
            if(binding.bmicategorydispaly.text == "Mild Thinness"
                || binding.bmicategorydispaly.text == "Moderate Thinness"
                || binding.bmicategorydispaly.text == "Severe Thinness"
            ) {
                binding.calneeded.visibility = View.VISIBLE
                binding.calneeded.text = "Calories needed: 2500 to 3000"

                binding.getmeal.setOnClickListener {
                    getdietUnderW_tw_th_male()
                }
            }else if(binding.bmicategorydispaly.text == "Overweight"
                || binding.bmicategorydispaly.text == "Suffering from Obesity"
            ){
                binding.calneeded.visibility = View.VISIBLE
                binding.calneeded.text = "Calories needed: 1400 to 1800"

                binding.getmeal.setOnClickListener {
                    Toast.makeText(this,"Calories",Toast.LENGTH_SHORT)
                        .show()
                    getdietOverW_tw_th_male()
                }
            }
        }

        //Female underweight 30 45
        else if (age > "30" && age < "45" && binding.genderdisplay.text == "Female") {
            binding.agedisplay.text = "Age Group: 30 - 45"
            if (binding.bmicategorydispaly.text == "Mild Thinness"
                || binding.bmicategorydispaly.text == "Moderate Thinness"
                || binding.bmicategorydispaly.text == "Severe Thinness"
            ) {
                binding.calneeded.visibility = View.VISIBLE
                binding.calneeded.text = "Calories needed: 1700 to 2400"

                binding.getmeal.setOnClickListener {
                    getdietUnderW_th_forty_Female()
                }
            }
        }
        //Male underweight 30 45
        else if (age > "30" && age < "45" && binding.genderdisplay.text == "Male") {
            binding.agedisplay.text = "Age Group: 30 - 45"
            if (binding.bmicategorydispaly.text == "Mild Thinness"
                || binding.bmicategorydispaly.text == "Moderate Thinness"
                || binding.bmicategorydispaly.text == "Severe Thinness"
            ) {
                binding.calneeded.visibility = View.VISIBLE
                binding.calneeded.text = "Calories needed: 1800 to 2500"

                binding.getmeal.setOnClickListener {
                    getdietUnderW_th_fortyMale()
                }
            }

        }

        else if (age > "15" && age < "20") {
            binding.agedisplay.text = "Age Group: 15 - 20"
            binding.getmeal.visibility = View.GONE

        }

        binding.bmidisplay.text = "%.1f".format(intbmi)
        binding.recalBMI.setOnClickListener {
            val intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    //Female Overweight 20 - 30
    private fun getdietOverW_tw_th_female() {
        bindingDialogFO = DialogOverwtwThFemaleBinding.inflate(layoutInflater)
        dbRef = FirebaseFirestore.getInstance()
        val colRef = dbRef.collection("Female_overweight(20-30)")
        //Get breakfast data
        colRef.document("2Y7lN0pHQ9Bop2xWacQy")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogFO.getBreakfast.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }
        //Get lunch data
        colRef.document("XPih9eTskBRGd5tlZOy7")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogFO.getLunch.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get snack data
        colRef.document("LoxDQFTzny2fcUevzslp")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogFO.getSnack.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get dinner data
        colRef.document("zM7GULbeUm2Ged68FKC8")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogFO.getDinner.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_overwtw_th_female, null)
        val mBuilder = AlertDialog.Builder(
            this,
            android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar
        )
            .setView(mDialogView)
        mBuilder.setView(bindingDialogFO.root)
        val mAlertDialog = mBuilder.show()
        bindingDialogFO.cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    //Male Overweight 30 - 45
    private fun getdietOverW_tw_th_male() {
        bindingDialogMO = DialogOverwtwThBinding.inflate(layoutInflater)
        dbRef = FirebaseFirestore.getInstance()
        val colRef = dbRef.collection("Male_overweight(20-30)")
        //Get breakfast data
        colRef.document("1qg0fSpshaPqILDoFGMO")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMO.getBreakfast.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get lunch data
        colRef.document("R542MAjKO4GTCxRC3GRW")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMO.getLunch.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get snack data
        colRef.document("stlZU93vrR8jT1fTqtFB")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMO.getSnack.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get dinner data
        colRef.document("X9LcRm8NXjCf1BLxFC7I")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMO.getDinner.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_overwtw_th, null)
        val mBuilder = AlertDialog.Builder(this,
            android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar
        ).setView(mDialogView)
        mBuilder.setView(bindingDialogMO.root)
        val mAlertDialog = mBuilder.show()
        bindingDialogMO.cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    //Female underweight 30 - 45
    private fun getdietUnderW_th_forty_Female() {
        bindingDialogFU = DialogUnderwthFortyFemaleBinding.inflate(layoutInflater)
        dbRef = FirebaseFirestore.getInstance()
        val colRef = dbRef.collection("Female_underweight(30-45)")
        //Get breakfast data
        colRef.document("rGVPkeeBjB502s1VL9Ss")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogFU.getBreakfast.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get lunch data
        colRef.document("uHKop8WDYC1yh0b6BNVX")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogFU.getLunch.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get snack data
        colRef.document("gzr6UnrLWXtoYKQv2ikg")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogFU.getSnack.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get dinner data
        colRef.document("FZbfeMcp85UglwN3yDDz")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogFU.getDinner.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_underwth_forty_female, null)
        val mBuilder = AlertDialog.Builder(
            this,
            android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar
        )
            .setView(mDialogView)
        mBuilder.setView(bindingDialogFU.root)
        val mAlertDialog = mBuilder.show()
        bindingDialogFU.cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    //Male underweight 30 - 45
    private fun getdietUnderW_th_fortyMale() {
        bindingDialogMaleU = DialogMUnderweight3045Binding.inflate(layoutInflater)
        dbRef = FirebaseFirestore.getInstance()
        val colRef = dbRef.collection("Male_underweight(30-45)")
        //Get breakfast data
        colRef.document("BykZl9sQuy4jUDpGjuui")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMaleU.getBreakfast.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }
        //Get lunch data
        colRef.document("KLz1USgUbRuxVeCFzcTn")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMaleU.getLunch.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get snack data
        colRef.document("SfRZteokXPGbJHL5TIL3")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMaleU.getSnack.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get dinner data
        colRef.document("GmwCAVVadvggaukoR7CN")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    @get:Exclude
                    bindingDialogMaleU.getDinner.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_m_underweight_30_45, null)
        val mBuilder = AlertDialog.Builder(
            this,
            android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar
        )
            .setView(mDialogView)
        mBuilder.setView(bindingDialogMaleU.root)
        val mAlertDialog = mBuilder.show()
        bindingDialogMaleU.cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    //Male Underweight 20 - 30
    private fun getdietUnderW_tw_th_male() {
        bindingDialogMU = DialogUnderwtwThMaleBinding.inflate(layoutInflater)
        dbRef = FirebaseFirestore.getInstance()
        val colRef = dbRef.collection("Male_underweight(20-30)")
        //Get breakfast data
        colRef.document("cxWMEdLpZI3pKpSLhLTu")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMU.getBreakfast.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }
        //Get lunch data
        colRef.document("5ZsdDQHV6SFe5i6bSh1T")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMU.getLunch.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get snack data
        colRef.document("STc9FSja6fdrITQ5UVyg")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMU.getSnack.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        //Get dinner data
        colRef.document( "WlQikjTKIh8tKQu2dRO8")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialogMU.getDinner.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_underwtw_th_male, null)
        val mBuilder = AlertDialog.Builder(
            this,
            android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar
        )
            .setView(mDialogView)
        mBuilder.setView(bindingDialogMU.root)


        val mAlertDialog = mBuilder.show()
        bindingDialogMU.cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    //Female Underweight 20 - 30
    private fun getdietUnderW_tw_th() {

        bindingDialog = DialogUnderwtwThBinding.inflate(layoutInflater)
        //getting data from firebase
        dbRef = FirebaseFirestore.getInstance()
        val colRef = dbRef.collection("underweight_diet(20-30)")
        //Get breakfast data
        colRef.document("vHPD61kuQsXPvlxPpo2b")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialog.getbreakfast.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }
        //Get lunch data
        colRef.document("aYZfIpXb8s8flHLgYUC9\n")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialog.getlunch.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }
        //Get snack data
        colRef.document("OSQDQPRz2haAOPvNtWwf")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialog.getSnack.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }
        //Get dinner data

        colRef.document("9861O2Zslv2TehclPPhh")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    bindingDialog.getDinner.text = it.data.toString()
                    Log.d(TAG, "Your data:  ${it.data}")
                } else {
                    Log.d(TAG, "No such document exist")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "get failed with", it)
            }

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_underwtw_th, null)
        val mBuilder = AlertDialog.Builder(
            this,
            android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar
        ).setView(mDialogView)
        mBuilder.setView(bindingDialog.root)
        val mAlertDialog = mBuilder.show()
        bindingDialog.cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}