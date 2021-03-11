package com.aavash.smartparkingclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class SignupActivity : AppCompatActivity() {

    private lateinit var etEmailReg:EditText
    private lateinit var etPasswordReg:EditText
    private lateinit var etVehicleReg:EditText
  //  private lateinit var etVehicle:EditText
    private lateinit var etLicenseReg:EditText

    private lateinit var btnregister:Button
    private lateinit var btnloginn: Button

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        val database = FirebaseDatabase.getInstance()
        val saveuser=database.getReference("userDetails")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        etEmailReg=findViewById(R.id.etEmailReg)

        etPasswordReg=findViewById(R.id.etPasswordReg)
        etVehicleReg=findViewById(R.id.etVehicleReg)
        etLicenseReg=findViewById(R.id.etLicenseReg)

        btnregister=findViewById(R.id.btnregister)
        btnloginn=findViewById(R.id.btnloginn)

        val amount:Long=1000

        auth = Firebase.auth

        val email=etEmailReg.text.toString()
        val vehicle=etVehicleReg.text.toString()
        val license=etLicenseReg.text.toString()


        btnregister.setOnClickListener {

            auth.createUserWithEmailAndPassword(etEmailReg.text.toString(),etPasswordReg.getText().toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@SignupActivity, "Authentication success", Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser


                            val userid = FirebaseAuth.getInstance().currentUser.uid

                          //  var id=userid

                            var model=UserData(etEmailReg.getText().toString(),etVehicleReg.getText().toString(),etLicenseReg.getText().toString(),amount)
                           saveuser.child(userid.toString()!!).setValue(model)

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
                    }



           // addUserData()
            closeKeyBoard()


        }

        btnloginn.setOnClickListener {
            startActivity(
                    Intent(
                            this@SignupActivity,
                            LoginActivity::class.java
                    )
            )
            closeKeyBoard()
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
    fun addUserData(){


    }
     fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}