package com.vpppcoe.buzzerapp

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import com.vpppcoe.buzzerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database : FirebaseDatabase
    private lateinit var countDownTimer: CountDownTimer
    private val initialTime: Long = 30 * 1000 // 30 seconds in milliseconds
    private var timeRemaining: Long = initialTime
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        val ref = database.getReference("USERS").child("responses")
        val time = ref.child("time")
        val name = ref.child("name")

        binding.restartTimer.setOnClickListener {
            restartTimer()
        }

        binding.buttonPlaySound.setOnClickListener {
            startTimer()
//            playMusic(applicationContext, R.raw.clock_sound)
        }

        binding.saveResponse.setOnClickListener {
            val currentTime = binding.timer.text.toString()
            time.setValue(currentTime).addOnCompleteListener {
                stopMusic()
                playMusicOnce(this, R.raw.question_locked_sound)
                stopTimer()
            }
        }

    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                updateTimer()

                // Play sound on every tick
                playMusic(this@MainActivity, R.raw.clock_sound)
            }

            override fun onFinish() {
                // Timer finished, do whatever you want here
                binding.timer.text = "Time's up!"
                pauseMusic()
            }
        }.start()
    }

    private fun stopTimer() {
        countDownTimer.cancel()
        binding.timer.text = "DONE"
    }


    private fun updateTimer() {
        val seconds = timeRemaining / 1000
        binding.timer.text = String.format("%02d:%02d", seconds / 60, seconds % 60)
    }

    private fun restartTimer() {
        countDownTimer.cancel()
        timeRemaining = initialTime
        startTimer()
        restartMusic()
    }


    fun playMusic(context: Context, resourceId: Int) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resourceId)
        }
        mediaPlayer?.start()
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun restartMusic() {
        mediaPlayer?.seekTo(0)
        mediaPlayer?.start()
    }

    fun stopMusic() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun playMusicOnce(context: Context, resourceId: Int) {
        stopMusic()

        mediaPlayer = MediaPlayer.create(context, resourceId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            // Release the MediaPlayer when the playback is completed
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}