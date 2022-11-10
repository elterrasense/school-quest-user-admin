package com.example.schoolquest_useradmin

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.schoolquest_useradmin.R.style.Theme_SchoolQuest_UserAdmin
import com.example.schoolquest_useradmin.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(Theme_SchoolQuest_UserAdmin)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        //val btEnviar = findViewById<Button>(R.id.ButtonResgisterAlumne1)

        binding.ButtonResgisterAlumne1.setOnClickListener{
            val email : String =
                binding.inputEmail.text.toString()//findViewById<TextInputEditText>(R.id.inputEmail).text.toString()
            val password1: String =
                binding.inputPassword.text.toString()//findViewById<TextInputEditText>(R.id.inputPassword).text.toString()
            val password2: String =
                binding.inputRepeatPassword.text.toString()//findViewById<TextInputEditText>(R.id.inputRepeatPassword).text.toString()

            //Verificar format del correu
            val pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`\\{|\\}~]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$".toRegex()
            if (pattern.containsMatchIn(email)) {
                //Verificar que les contrasenyes coincideixin i no siguin vuides
                when (password1) {
                    "" -> {
                        dialogBuilder("Error", "La contrasenya no pot ser vuida")
                    }
                    password2 -> {
                        crearUsuari(email, password2)
                    }
                    else -> {
                        dialogBuilder("Error", "Les contrasenyes no coincideixen")
                    }
                }
            } else {
                dialogBuilder("Error", "El correu no te un format válid")
            }
        }
    }

    private fun dialogBuilder(tittle: String, message: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(tittle)
            .setMessage(message)
            .create()
        dialog.show()
    }

    private fun crearUsuari(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Usuari creat correctament
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Snackbar.make(binding.root, "Usuari creat correctament.", 5000)
                        .show()
                } else {
                    //Error creant l'usuari
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Snackbar.make(binding.root, "Error de connexió.", 5000)
                        .show()
                }
            }
    }



}


