package com.aavash.smartparkingclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var etEmailReg:EditText
    private lateinit var etPasswordReg:EditText
    private lateinit var etVehicleReg:EditText
    private lateinit var btnregister:Button
    private lateinit var btnloginn: Button

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        etEmailReg=findViewById(R.id.etEmailReg)
        etVehicleReg=findViewById(R.id.etVehicleReg)
        etPasswordReg=findViewById(R.id.etPasswordReg)
        btnregister=findViewById(R.id.btnregister)
        btnloginn=findViewById(R.id.btnloginn)

        auth = Firebase.auth




        btnregister.setOnClickListener {
            auth.createUserWithEmailAndPassword(etEmailReg.text.toString(),etPasswordReg.getText().toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@SignupActivity, "Authentication success", Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser
                            startActivity(
                                    Intent(
                                            this@SignupActivity,
                                            LoginActivity::class.java
                                    )
                            )

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(this@SignupActivity, "Authentication failed", Toast.LENGTH_SHORT).show()

                        }

                        // ...
                    }
        }

        btnloginn.setOnClickListener {
            startActivity(
                    Intent(
                            this@SignupActivity,
                            LoginActivity::class.java
                    )
            )
        }



    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
         //   reload();
        }
    }
}