package com.vpppcoe.buzzerapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.vpppcoe.buzzerapp.databinding.ActivityRegistrationPageBinding

class RegistrationPage : AppCompatActivity() {

    private lateinit var binding : ActivityRegistrationPageBinding
    private lateinit var database : FirebaseDatabase

    //Shared preferences to store user login status
    companion object {
        private const val SHARED_PREFS_NAME = "BuzzerApp"
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences =
            applicationContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            if (binding.etGroupName.text.toString() == "technetics" && binding.etGrpNumber.text.toString() == "000") {
                startActivity(Intent(this, AdminControl::class.java))
                finish()
            } else {
                if (binding.etGroupName.text.isNotEmpty() && binding.etGrpNumber.text.isNotEmpty()) {
                    val groupNumber = binding.etGrpNumber.text.trim().toString().toInt()
                    val groupName = binding.etGroupName.text.trim().toString()
                    val editor = RegistrationPage.sharedPreferences.edit()
                    editor.putInt("GroupNumber", groupNumber)
                    editor.putString("GroupName", groupName)
                    editor.apply()
                    Toast.makeText(this, "Welcome to Technetics", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    binding.etGroupName.error = "Enter Group Name"
                    binding.etGrpNumber.error = "Enter Group Number"
                }
            }
        }

        database = FirebaseDatabase.getInstance()


    }

}