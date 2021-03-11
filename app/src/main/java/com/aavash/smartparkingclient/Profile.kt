package com.aavash.smartparkingclient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class Profile : AppCompatActivity() {
    private lateinit var btnsignout: Button
    private lateinit var tvUsers: TextView
    private lateinit var tvamount: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    lateinit var users:UserData

    val database = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvUsers = findViewById(R.id.tvUsers)
        tvamount = findViewById(R.id.tvamount)

        btnsignout = findViewById(R.id.btnsignout)

        auth = Firebase.auth

        //get user instance
        user = Firebase.auth.currentUser
        val bookeduser = user.uid


        //to get user email
        if (user != null) {
            // User is signed in
            tvUsers.setText(user.email)
        } else {
            // No user is signed in
            Toast.makeText(this@Profile, "No user found. Restart the app", Toast.LENGTH_SHORT).show()
        }

        btnsignout.setOnClickListener {
            signOut()
            Toast.makeText(this@Profile, "user has been signed out", Toast.LENGTH_SHORT).show()
            startActivity(
                    Intent(
                            this@Profile,
                            LoginActivity::class.java
                    )
            )
        }

//to get amount
        val userss = database.getReference("userDetails").orderByChild("email").equalTo(user.email);
        Log.d("userss",userss.toString())


        val myRef = database.getReference("userDetails")

//        getamount.addListenerForSingleValueEvent()
        //tvamount.setText(getamount.toString())

        val universityList: MutableList<UserData> = ArrayList()


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d("Key", "onChildAdded:" + snapshot.value);
//                Log.d("uid",user.uid.toString());
                universityList.clear()
                for (postSnapshot in snapshot.children) {

                    val university: UserData? = postSnapshot.getValue(UserData::class.java)
//                    postSnapshot.get

                    if(university?.email == user.email){
                             println(university?.amount)
                        tvamount.setText(university?.amount.toString())
                    }

//                    if (university != null) {
//
////                        Log.d("userdata", university)
//                        Log.d("useremail", user.email)
//                        if (university.email == user.email) {
//                            Log.d("email", university.licenceno);
//                        }
//                    }
//                    if (university != null) {
//                        universityList.add(university)
//                        println("universityyyy")
//                        print(universityList.size)
//                    }


                    // here you can access to name property like university.name
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun signOut() {
        auth.signOut()

    }
}