package com.example.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mainactivity.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding

    var intweight: Int = 55
    var intage: Int = 22
    var currentprogress: Int = 0

    var mintprogress: String = "170"
    var typeofuser: String = "0"
    var weight2: String = "55"
    var age2: String = "22"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.male.setOnClickListener {
            binding.male.background = resources.getDrawable(R.drawable.malefemalefocus)
            binding.female.background = resources.getDrawable(R.drawable.malefemalenotfocus)
            typeofuser = "Male"
        }
        binding.female.setOnClickListener {
            binding.female.background = resources.getDrawable(R.drawable.malefemalefocus)
            binding.male.background = resources.getDrawable(R.drawable.malefemalenotfocus)
            typeofuser = "Female"
        }

        binding.seekbarforheight.max = 300
        binding.seekbarforheight.progress = 170
        binding.seekbarforheight.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {

                binding.heightlayout.background = resources.getDrawable(R.drawable.malefemalefocus)
                binding.seekbarforheight.background = resources.getDrawable(R.drawable.malefemalenotfocus)


                currentprogress = progress
                mintprogress = java.lang.String.valueOf(currentprogress)
                binding.currentheight.text = mintprogress


            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })

        binding.incrementage.setOnClickListener {
            binding.Age.background = resources.getDrawable(R.drawable.malefemalefocus)
            intage += 1
            age2 = java.lang.String.valueOf(intage)
            binding.currentage.text = age2
        }

        binding.decrementage.setOnClickListener {
            binding.Age.background = resources.getDrawable(R.drawable.malefemalefocus)
            intage -= 1
            age2 = java.lang.String.valueOf(intage)
            binding.currentage.text = age2
        }

        binding.incremetweight.setOnClickListener {
            binding.weight.background = resources.getDrawable(R.drawable.malefemalefocus)
            intweight += 1
            weight2 = java.lang.String.valueOf(intweight)
            binding.currentweight.text = weight2
        }

        binding.decrementweight.setOnClickListener {
            binding.weight.background = resources.getDrawable(R.drawable.malefemalefocus)
            intweight -= 1
            weight2 = java.lang.String.valueOf(intweight)
            binding.currentweight.text = weight2
        }

        binding.calculatebmi.setOnClickListener {
            binding.calculatebmi.background = resources.getDrawable(R.drawable.malefemalefocus)
            if (typeofuser.equals("0")) {
                Toast.makeText(
                    this@BmiActivity, "Select your Gender first",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (mintprogress.equals("0")  ) {
                Toast.makeText(
                    this@BmiActivity, "Select valid height plz",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (intage <=15) {
                Toast.makeText(
                    this@BmiActivity, "Select valid Age ",
                    Toast.LENGTH_SHORT
                ).show()
            } else if ( intweight <35) {
                Toast.makeText(
                    this@BmiActivity, "Select valid weight",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                val intent = Intent(this, BmiResultActivity::class.java)

                intent.putExtra("gender", typeofuser)
                intent.putExtra("height", mintprogress)
                intent.putExtra("weight", weight2)
                intent.putExtra("age",age2)
                startActivity(intent)
                finish()
            }
        }
    }

}