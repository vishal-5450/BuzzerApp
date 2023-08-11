package com.vpppcoe.buzzerapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vpppcoe.buzzerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database : FirebaseDatabase
    private lateinit var countDownTimer: CountDownTimer
    private val initialTime: Long = 30 * 1000 // 30 seconds in milliseconds
    private var timeRemaining: Long = initialTime
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var vibrator: Vibrator

    //Shared preferences to store user login status
    companion object {
        private const val SHARED_PREFS_NAME = "BuzzerApp"
        lateinit var sharedPreferences: SharedPreferences
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MainActivity.sharedPreferences =
            applicationContext.getSharedPreferences(MainActivity.SHARED_PREFS_NAME, Context.MODE_PRIVATE)

        val groupNumber = MainActivity.sharedPreferences.getInt("GroupNumber", 0)
        val groupName = MainActivity.sharedPreferences.getString("GroupName", "vishal")

        database = FirebaseDatabase.getInstance()

        val clickAllowed = database.getReference("CLICKME")
        val ref = database.reference
        val time = ref.child("time")
        val grpName = ref.child("group_name")
        val grpNumber = ref.child("group_number")
        val question = ref.child("question")
        val option1 = ref.child("option1")
        val option2 = ref.child("option2")
        val option3 = ref.child("option3")
        val option4 = ref.child("option4")

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator


        clickAllowed.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val status = snapshot.value.toString().toBoolean()
                    if (status) {
                        binding.saveResponse.setBackgroundResource(R.drawable.round_button_bg)
                        binding.saveResponse.isClickable = true
                    } else {
                        binding.saveResponse.setBackgroundResource(R.drawable.round_button_bg_red)
                        binding.saveResponse.isClickable = false
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        question.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val question = snapshot.value.toString()
                    binding.tvQuestion.text = question
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        option1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val option1 = snapshot.value.toString()
                    binding.tvOption1.text = option1

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        option2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val option2 = snapshot.value.toString()
                    binding.tvOption2.text = option2

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        option3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val option3 = snapshot.value.toString()
                    binding.tvOption3.text = option3

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        option4.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val option4 = snapshot.value.toString()
                    binding.tvOption4.text = option4
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.saveResponse.setOnClickListener {
            val currentTime = binding.timer.text.toString()
            time.setValue(currentTime)
            grpName.setValue(groupName)
            grpNumber.setValue(groupNumber)
            if (vibrator.hasVibrator()) {
                // Vibrate for 500 milliseconds
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            Toast.makeText(this, "Fastest Fingers", Toast.LENGTH_SHORT).show()
        }

        //Logout button
        binding.timer.setOnClickListener {
            val editor = MainActivity.sharedPreferences.edit()
            editor.putInt("GroupNumber", 0)
            editor.putString("GroupName", "null")
            editor.apply()
            startActivity(Intent(this, RegistrationPage::class.java))
            finish()
        }
    }

//    private fun startTimer() {
//        countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                timeRemaining = millisUntilFinished
//                updateTimer()
//
//                // Play sound on every tick
//                playMusic(this@MainActivity, R.raw.clock_sound)
//            }
//
//            override fun onFinish() {
//                // Timer finished, do whatever you want here
//                binding.timer.text = "Time's up!"
//                pauseMusic()
//            }
//        }.start()
//    }
//
//    private fun stopTimer() {
//        countDownTimer.cancel()
//        binding.timer.text = "DONE"
//    }
//
//
//    private fun updateTimer() {
//        val seconds = timeRemaining / 1000
//        binding.timer.text = String.format("%02d:%02d", seconds / 60, seconds % 60)
//    }
//
//    private fun restartTimer() {
//        countDownTimer.cancel()
//        timeRemaining = initialTime
//        startTimer()
//        restartMusic()
//    }
//
//
//    fun playMusic(context: Context, resourceId: Int) {
//        if (mediaPlayer == null) {
//            mediaPlayer = MediaPlayer.create(context, resourceId)
//        }
//        mediaPlayer?.start()
//    }
//
//    fun pauseMusic() {
//        mediaPlayer?.pause()
//    }
//
//    fun restartMusic() {
//        mediaPlayer?.seekTo(0)
//        mediaPlayer?.start()
//    }
//
//    fun stopMusic() {
//        mediaPlayer?.release()
//        mediaPlayer = null
//    }
//
//    fun playMusicOnce(context: Context, resourceId: Int) {
//        stopMusic()
//
//        mediaPlayer = MediaPlayer.create(context, resourceId)
//        mediaPlayer?.start()
//        mediaPlayer?.setOnCompletionListener {
//            // Release the MediaPlayer when the playback is completed
//            mediaPlayer?.release()
//            mediaPlayer = null
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}