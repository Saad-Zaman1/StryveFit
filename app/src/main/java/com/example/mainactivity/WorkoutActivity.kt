package com.example.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.mainactivity.databinding.ActivityWorkoutBinding
import com.example.mainactivity.databinding.DialogCrunchesBinding
import com.example.mainactivity.databinding.DialogJacksBinding
import com.example.mainactivity.databinding.DialogMclimberBinding
import com.example.mainactivity.databinding.DialogPushupsBinding
import com.example.mainactivity.databinding.DialogRcrunchesBinding
import com.example.mainactivity.databinding.DialogSquatsBinding

class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutBinding
    private lateinit var bindingDialog: DialogPushupsBinding
    private lateinit var bindingDialogM: DialogMclimberBinding
    private lateinit var bindingDialogJ: DialogJacksBinding
    private lateinit var bindingDialogS: DialogSquatsBinding
    private lateinit var bindingDialogC: DialogCrunchesBinding
    private lateinit var bindingDialogR: DialogRcrunchesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bvector.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        binding.cardPushups.setOnClickListener{
            desDialogPushups()
        }
        binding.cardMclimers.setOnClickListener{
            desDialogMclimbers()
        }
        binding.cardJJacks.setOnClickListener{
            desDialogJacks()
        }
        binding.cardSquats.setOnClickListener{
            desDialogSquats()
        }
        binding.cardCrunches.setOnClickListener{
            desDialogCrunches()
        }
        binding.cardRcrunches.setOnClickListener{
            desDialogRcrinches()
        }

    }

    private fun desDialogRcrinches() {
        bindingDialogR = DialogRcrunchesBinding.inflate(layoutInflater)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rcrunches,null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        mBuilder.setView(bindingDialogR.root)
        val mAlertDialog = mBuilder.show()
        bindingDialogR.cancel.setOnClickListener{
            mAlertDialog.dismiss()
        }
    }

    private fun desDialogCrunches() {
        bindingDialogC = DialogCrunchesBinding.inflate(layoutInflater)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_crunches,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        mBuilder.setView(bindingDialogC.root)
        val mAlertDialog = mBuilder.show()
        bindingDialogC.cancel.setOnClickListener{
            mAlertDialog.dismiss()
        }
    }

    private fun desDialogSquats() {
        bindingDialogS = DialogSquatsBinding.inflate(layoutInflater)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_squats,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        mBuilder.setView(bindingDialogS.root)
        val mAlertDialog = mBuilder.show()
        bindingDialogS.cancel.setOnClickListener{
            mAlertDialog.dismiss()
        }
    }

    private fun desDialogJacks() {
        bindingDialogJ = DialogJacksBinding.inflate(layoutInflater)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_jacks,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        mBuilder.setView(bindingDialogJ.root)
        val mAlertDialog = mBuilder.show()
        bindingDialogJ.cancel.setOnClickListener{
            mAlertDialog.dismiss()
        }
    }

    private fun desDialogMclimbers() {
        bindingDialogM = DialogMclimberBinding.inflate(layoutInflater)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_mclimber,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        mBuilder.setView(bindingDialogM.root)
        val mAlertDialog = mBuilder.show()
        bindingDialogM.cancel.setOnClickListener{
            mAlertDialog.dismiss()
        }
    }

    private fun desDialogPushups() {
        bindingDialog = DialogPushupsBinding.inflate(layoutInflater)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_pushups,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView((mDialogView))
        mBuilder.setView(bindingDialog.root)
        val mAlertDialog = mBuilder.show()
        bindingDialog.cancel.setOnClickListener{
            mAlertDialog.dismiss()
        }

    }
}