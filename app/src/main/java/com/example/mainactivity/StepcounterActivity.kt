package com.example.mainactivity

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mainactivity.databinding.ActivityStepcounterBinding
import com.example.mainactivity.databinding.DialogCrunchesBinding
import com.example.mainactivity.databinding.PopupgoalBinding

class StepcounterActivity : AppCompatActivity(), SensorEventListener {

    private val ACTIVITY_RECOGNITION_REQDUEST_CODE: Int = 100
    private lateinit var binding: ActivityStepcounterBinding
    var sensorManager: SensorManager? = null
    private var running: Boolean = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStepcounterBinding.inflate(layoutInflater)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if (isPermissionGranted()) {
            requestPermissions()
        }


        loadData()
        resetSteps()
        setContentView(binding.root)

        //Goal setting
        binding.goal.setOnClickListener {
            val v = if (binding.changeGoalet.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.changeGoalet.visibility = v
            val i = if (binding.SelectGoal.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.SelectGoal.visibility = i
        }


        binding.SelectGoal.setOnClickListener {
            val v = binding.changeGoalet.text.toString()
            if(v > "1500"){
            val k = if (binding.changeGoalet.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.changeGoalet.visibility = k
            val i = if (binding.SelectGoal.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.SelectGoal.visibility = i
            binding.totalMax.text = v
            binding.circularProgressBar.progressMax  = v.toFloat()
            }
            else{
                Toast.makeText(this,"Goal must be greater than 1500",Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

  /*  private fun goal() {
        bindingDialog = PopupgoalBinding.inflate(layoutInflater)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.popupgoal, null)
        val mBuilder = AlertDialog.Builder(this).setView((mDialogView))
        mBuilder.setView(bindingDialog.root)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window!!.setGravity(Gravity.BOTTOM);

        binding.totalMax.text
        val np = bindingDialog.numberpicker
        val values = arrayOfNulls<String>(50000)
        for (i in values.indices) {
            val number = (i * 1000).toString()
            values[i] = number
        }

        np.minValue = 0
        np.maxValue = 50
        np.wrapSelectorWheel = false
        np.displayedValues = values

        np.setOnValueChangedListener { picker, oldVal, newVal ->
//                Integer.parseInt(values[newVal]);
            bindingDialog.selectgoalbtn.setOnClickListener {
                binding.totalMax.text = newVal.toString() + "000"
                mAlertDialog.dismiss()
            }
        }
    }

*/

    private fun isPermissionGranted(): Boolean {
// check user have permission enabled
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACTIVITY_RECOGNITION
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                ACTIVITY_RECOGNITION_REQDUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACTIVITY_RECOGNITION_REQDUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                }
            }
        }
    }

    //After onStart() callback
    //Just before activity on running state
    override fun onResume() {
        super.onResume()
        running = true
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)



        if (stepSensor == null) {
            Toast.makeText(this, "No sensor detected in this device ", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    //When another activity comes in foreground
//    override fun onPause() {
//        super.onPause()
//        running = false
//        sensorManager?.unregisterListener(this)
//    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running) {
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            binding.steptaken.text = currentSteps.toString()
            binding.circularProgressBar.apply {
                setProgressWithAnimation(currentSteps.toFloat())
            }
            val cal = currentSteps * 0.04
            binding.caloriestv.text = "%.2f".format(cal)

            val distance = currentSteps * 0.0008
            binding.distance.text = "%.1f".format(distance)

            val duration = currentSteps / 120
            binding.duration.text = duration.toString()

        }
    }

    private fun resetSteps() {
        binding.steptaken.setOnClickListener {
            Toast.makeText(this, "Long tab to reset steps", Toast.LENGTH_SHORT).show()
            binding.steptaken.setOnLongClickListener {
                previousTotalSteps = totalSteps
                binding.steptaken.text = 0.toString()
                binding.circularProgressBar.apply {
                    setProgressWithAnimation(0.toFloat())
                }
                binding.duration.text = 0.toString()
                binding.distance.text = 0.toString()
                binding.caloriestv.text = 0.toString()
                saveData()
                true
            }
        }
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("step", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("currentstep", previousTotalSteps)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("step", Context.MODE_PRIVATE)
        val savedNumber: Float = sharedPreferences.getFloat("currentstep", 0F)
        Log.d("MainActivity", "$savedNumber")
        previousTotalSteps = savedNumber
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //
    }
}