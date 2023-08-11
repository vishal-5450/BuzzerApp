package com.vpppcoe.buzzerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vpppcoe.buzzerapp.databinding.ActivityAdminControlBinding

class AdminControl : AppCompatActivity() {

    private lateinit var binding : ActivityAdminControlBinding
    private lateinit var database : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionData = arrayListOf<String>(
            "How long did it take chandrayaan-3 to reach the lunar orbit after it's journey began?",
            "Who is a education minister of India 2023?",
            "Who is the captain of women's  Indian Cricket  team 2022?",
            "Who was the ISRO chairman during the Mars Orbiter Mission(Mangalyaan)?",
            "Who is the current army chief of india?"
        )

        val question1Options = arrayListOf<String>("13Days", "23 Days", "33 Days", "44 Days")
        val question2Options = arrayListOf<String>("Shri Narendra  singh", "Dharmendra Pradhan", "Sharad Pawar", "Sandeep singh")
        val question3Options = arrayListOf<String>("Harmanpreet Kaur", "Smriti Mandhana", "Yastik Bhatia", "Harleen Deol")
        val question4Options = arrayListOf<String>("Dr.k.Radhakrishnan", "Dr.A.S.Kiran Kumar", "Dr.k.Kasturirangan", "Dr.sivan K.")
        val question5Options = arrayListOf<String>("General Manoj Pande", "General Bipin Rawat", "General Rob Lockhart", "General Amardeep Singh Aujla")

        database = FirebaseDatabase.getInstance()

        binding.turnOffClick.setOnClickListener {
            val click = database.getReference("CLICKME")
            click.setValue("false")
        }

        binding.turnOnClick.setOnClickListener {
            val click = database.getReference("CLICKME")
            click.setValue("true")
        }

        binding.clearAllFields.setOnClickListener {
            database.getReference("question").setValue("")
            database.getReference("option1").setValue("")
            database.getReference("option2").setValue("")
            database.getReference("option3").setValue("")
            database.getReference("option4").setValue("")
        }

        binding.hideOptions.setOnClickListener {
            database.getReference("option1").setValue("")
            database.getReference("option2").setValue("")
            database.getReference("option3").setValue("")
            database.getReference("option4").setValue("")
        }

        binding.question1.setOnClickListener {
            database.getReference("question").setValue(questionData[0])
        }

        binding.question2.setOnClickListener {
            database.getReference("question").setValue(questionData[1])
        }

        binding.question3.setOnClickListener {
            database.getReference("question").setValue(questionData[2])
        }

        binding.question4.setOnClickListener {
            database.getReference("question").setValue(questionData[3])
        }

        binding.question5.setOnClickListener {
            database.getReference("question").setValue(questionData[4])
        }

        binding.options1.setOnClickListener {
            database.getReference("option1").setValue(question1Options[0])
            database.getReference("option2").setValue(question1Options[1])
            database.getReference("option3").setValue(question1Options[2])
            database.getReference("option4").setValue(question1Options[3])
        }

        binding.options2.setOnClickListener {
            database.getReference("option1").setValue(question2Options[0])
            database.getReference("option2").setValue(question2Options[1])
            database.getReference("option3").setValue(question2Options[2])
            database.getReference("option4").setValue(question2Options[3])
        }

        binding.options3.setOnClickListener{
            database.getReference("option1").setValue(question3Options[0])
            database.getReference("option2").setValue(question3Options[1])
            database.getReference("option3").setValue(question3Options[2])
            database.getReference("option4").setValue(question3Options[3])
        }

        binding.options4.setOnClickListener {
            database.getReference("option1").setValue(question4Options[0])
            database.getReference("option2").setValue(question4Options[1])
            database.getReference("option3").setValue(question4Options[2])
            database.getReference("option4").setValue(question4Options[3])
        }

        binding.options5.setOnClickListener{
            database.getReference("option1").setValue(question5Options[0])
            database.getReference("option2").setValue(question5Options[1])
            database.getReference("option3").setValue(question5Options[2])
            database.getReference("option4").setValue(question5Options[3])
        }

    }
}