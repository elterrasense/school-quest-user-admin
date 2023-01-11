package com.example.schoolquest_useradmin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.schoolquest_useradmin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SchoolQuest_UserAdmin)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Botó per obrir Login_Alumne.kt
        binding.ButtonLogin1.setOnClickListener {
            val intent = Intent(this, Students::class.java)
            startActivity(intent)
        }

        //Botó per obrir login_professor.kt
        binding.ButtonLogin2.setOnClickListener {
            val intent = Intent(this, Professor::class.java)
            startActivity(intent)
        }
    }
}
