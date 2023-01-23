package com.example.schoolquest_useradmin

import android.app.AlertDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.schoolquest_useradmin.databinding.ActivityStudentsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Students : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SchoolQuest_UserAdmin)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        val binding = ActivityStudentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val btEnviar = binding.ButtonResgisterAlumne1

        btEnviar.setOnClickListener{
            val email : String = binding.inputEmail.text.toString()
            val password1: String = binding.inputPassword.text.toString()
            val password2: String = binding.inputRepeatPassword.text.toString()

            //Verificar format del correu
            val pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`\\{|\\}~]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$".toRegex()
            if (pattern.containsMatchIn(email)) {
                //Verificar que les contrasenyes coincideixin i no siguin vuides
                when (password1) {
                    "" -> {
                        dialogBuilder("Error", "La contrasenya no pot ser vuida")
                    }
                    password2 -> {
                        crearUsuari(email, password2, binding)
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

    private fun crearUsuari(email: String, password: String, binding: ActivityStudentsBinding) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    db.collection("Students").document(auth.uid.toString()).set(
                        hashMapOf("User" to binding.inputEmail.text.toString(),
                            "Name" to binding.inputName.text.toString(),
                            "Surname1" to binding.inputSurname1.text.toString(),
                            "Surname2" to binding.inputSurname2.text.toString())
                    )

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    Snackbar.make(this.findViewById(android.R.id.content), "Error d'autenticació.", 5000)
                        .show()
                }
            }
    }



}