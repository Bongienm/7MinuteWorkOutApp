package com.example.a7minuteworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        handleStartExercise()
    }

    private fun handleStartExercise() {
        val flStartButton : FrameLayout = findViewById(R.id.flStart)
        flStartButton.setOnClickListener{
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.apply {
            flStart.setOnClickListener {
//                Toast.makeText(this@MainActivity, "Start Exercise", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, ExerciseActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}