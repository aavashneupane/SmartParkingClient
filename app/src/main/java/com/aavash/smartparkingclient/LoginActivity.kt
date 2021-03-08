package com.aavash.smartparkingclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmailLog: EditText
    private lateinit var etPasswordLog: EditText
    private lateinit var btnlogin: Button


    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmailLog=findViewById(R.id.etEmailLog)
        etPasswordLog=findViewById(R.id.etPasswordLog)
        btnlogin=findViewById(R.id.btnlogin)


        auth = Firebase.auth



        btnlogin.setOnClickListener {
            auth.signInWithEmailAndPassword(etEmailLog.text.toString(),etPasswordLog.getText().toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@LoginActivity, "Login success", Toast.LENGTH_SHORT).show()
                          //  val user = auth.currentUser
                            startActivity(
                                    Intent(
                                            this@LoginActivity,
                                            MainActivity::class.java
                                    )
                            )

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()

                        }

                        // ...
                    }
        }






    }

}