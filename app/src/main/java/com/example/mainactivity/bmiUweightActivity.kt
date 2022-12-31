package com.example.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.mainactivity.databinding.ActivityBmiUweightBinding
import com.example.mainactivity.databinding.UwpopuptipsBinding

class bmiUweightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiUweightBinding
    private lateinit var bindingDialog: UwpopuptipsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiUweightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tips.setOnClickListener{
            TipsDialog()
        }
    }


    private fun TipsDialog(){
        bindingDialog = UwpopuptipsBinding.inflate(layoutInflater)
        //Inflate dialod with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.uwpopuptips,null)
        //Alert dialog builder
        val mBuilder = AlertDialog.Builder(this, android.R.style.Theme_Light_NoTitleBar)
            .setView((mDialogView))
        mBuilder.setView(bindingDialog.root)

        //Scroll text
        bindingDialog.tipstv.movementMethod = ScrollingMovementMethod()

        //show dialog
        val mAlertDialog = mBuilder.show()
        bindingDialog.cancel.setOnClickListener{
            mAlertDialog.dismiss()
        }

    }
}