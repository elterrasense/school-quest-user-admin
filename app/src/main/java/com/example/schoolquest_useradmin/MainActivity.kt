package com.example.schoolquest_useradmin

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.inflate
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import com.example.schoolquest_useradmin.R.style.Theme_SchoolQuest_UserAdmin
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(Theme_SchoolQuest_UserAdmin)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        val btEnviar = findViewById<Button>(R.id.ButtonResgisterAlumne1)

        btEnviar.setOnClickListener{
            val email : String = findViewById<TextInputEditText>(R.id.inputEmail).text.toString()
            val password1: String = findViewById<TextInputEditText>(R.id.inputPassword).text.toString()
            val password2: String = findViewById<TextInputEditText>(R.id.inputRepeatPassword).text.toString()

            if (password1==password2)
            crearUsuari(email, password2)
            TODO("else con manejador de errores, snackbar...")
        }

    }

    private fun crearUsuari(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }



}


