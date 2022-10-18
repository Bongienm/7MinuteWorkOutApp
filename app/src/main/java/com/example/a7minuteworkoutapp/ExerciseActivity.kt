package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener{
            onBackPressed()
        }
        setupRestView()
    }

    private fun setupRestView() {
        binding?.apply {
            // Show Resting
            flRestView?.visibility = View.VISIBLE
            textviewGetready?.visibility = View.VISIBLE

            // Hide Exercise
            tvExerciseName?.visibility = View.INVISIBLE
            flExerciseView?.visibility = View.INVISIBLE
            ivImage?.visibility = View.INVISIBLE

            if (restTimer != null){
                restTimer?.cancel()
                restProgress = 0
            }

            // currentExercisePosition++
            setRestProgressBar()
        }
    }

    private fun setRestProgressBar() {
        binding?.apply {
            progressBar?.progress = restProgress
            restTimer = object :CountDownTimer(5000,1000){
                override fun onTick(p0: Long) {
                    restProgress++
                    progressBar?.progress = 5 - restProgress
                    tvTimer?.text = (5 - restProgress).toString()
                }

                override fun onFinish() {
//                    Toast.makeText(
//                        this.baseContext,
//                        "Start Exercise!",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    setupExerciseView()
                }
            }.start()
        }
    }

    private fun setupExerciseView() {
        binding?.apply {
            // Hide Rest View
            this.flRestView?.visibility = View.INVISIBLE
            textviewGetready?.visibility = View.INVISIBLE

            // Show Exercise View
            tvExerciseName?.visibility = View.VISIBLE
            flExerciseView?.visibility = View.VISIBLE
            ivImage?.visibility = View.VISIBLE

            if (exerciseTimer != null){
                exerciseTimer?.cancel()
                exerciseProgress=0
            }

            /// Learn Later: Safe Unwrapping
            /*exerciseList?.let {
                ivImage?.setImageResource(it[currentExercisePosition].getImage())
                tvExerciseName?.text = it[currentExercisePosition].getName()
            }*/
            /////

            // Force Unwrapping of a null value !!
            ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
            tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

            setExerciseProgressBar()
        }

    }

    private fun setExerciseProgressBar() {
        binding?.apply {
            progressBarExercise?.progress = exerciseProgress

            exerciseTimer = object :CountDownTimer(10000,1000){
                override fun onTick(p0: Long) {
                    exerciseProgress++
                    progressBarExercise?.progress = 10 - exerciseProgress
                    tvTimerExercise?.text = (10 - exerciseProgress).toString()
                }

                override fun onFinish() {
                currentExercisePosition++
//                setupExerciseView()
                    if (currentExercisePosition < exerciseList?.size!! -1){
                        textviewGetready?.text = "Nice Workout. \nGet Ready for ${exerciseList!![currentExercisePosition].getName()}"
                        setupRestView()
                    } else{
                        Toast.makeText(
                            this@ExerciseActivity.baseContext,
                            "Congatulations! You Have Completed the 7 Minutes WorkOut.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.start()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        binding = null
    }

}
