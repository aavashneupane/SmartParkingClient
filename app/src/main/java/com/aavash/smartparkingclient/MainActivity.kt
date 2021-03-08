package com.aavash.smartparkingclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var btnsignout:Button
    private lateinit var tvUsers:TextView

    private lateinit var tvSlot1:TextView
    private lateinit var tvSlot2:TextView
    private lateinit var tvSlot3:TextView

    private lateinit var tvBook1:TextView
    private lateinit var tvBook2:TextView
    private lateinit var tvBook3:TextView

    private lateinit var btnbook1:Button
    private lateinit var btnbook2:Button
    private lateinit var btnbook3:Button

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {

        val database = FirebaseDatabase.getInstance()
        val bookedby1 = database.getReference("ParkingIOT/SLOT1/bookedby/name")
        val getin1 = database.getReference("ParkingIOT/SLOT1/bookedby/getin")
        val getout1 = database.getReference("ParkingIOT/SLOT1/bookedby/getout")
        val vacant1 = database.getReference("ParkingIOT/SLOT1/vacant")


        val bookedby2 = database.getReference("ParkingIOT/SLOT2/bookedby/name")
        val getin2 = database.getReference("ParkingIOT/SLOT2/bookedby/getin")
        val getout2 = database.getReference("ParkingIOT/SLOT2/bookedby/getout")
        val vacant2 = database.getReference("ParkingIOT/SLOT2/vacant")

        val bookedby3 = database.getReference("ParkingIOT/SLOT3/bookedby/name")
        val getin3 = database.getReference("ParkingIOT/SLOT3/bookedby/getin")
        val getout3 = database.getReference("ParkingIOT/SLOT3/bookedby/getout")
        val vacant3 = database.getReference("ParkingIOT/SLOT3/vacant")

        val slot1 = database.getReference("ParkingIOT/SLOT1/status")
        val slot2 = database.getReference("ParkingIOT/SLOT2/status")
        val slot3 = database.getReference("ParkingIOT/SLOT3/status")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSlot1=findViewById(R.id.tvslot1)
        tvSlot2=findViewById(R.id.tvslot2)
        tvSlot3=findViewById(R.id.tvslot3)

        tvBook1=findViewById(R.id.tvbook1)
        tvBook2=findViewById(R.id.tvbook2)
        tvBook3=findViewById(R.id.tvbook3)

        btnbook1=findViewById(R.id.btnbook1)
        btnbook2=findViewById(R.id.btnbook2)
        btnbook3=findViewById(R.id.btnbook3)

        btnbook1.setVisibility(View.GONE)
        btnbook2.setVisibility(View.GONE)
        btnbook3.setVisibility(View.GONE)

        tvUsers=findViewById(R.id.tvUsers)
        btnsignout=findViewById(R.id.btnsignout)
        auth = Firebase.auth

        //get user instance
        user = Firebase.auth.currentUser
        val bookeduser=user.email

        //to get user email
        if (user != null) {
               // User is signed in
            tvUsers.setText(user.email)
        } else {
            // No user is signed in
            Toast.makeText(this@MainActivity, "No user found. Restart the app", Toast.LENGTH_SHORT).show()
        }




        btnsignout.setOnClickListener {
                signOut()
            Toast.makeText(this@MainActivity, "user has been signed out", Toast.LENGTH_SHORT).show()
            startActivity(
                    Intent(
                            this@MainActivity,
                            LoginActivity::class.java
                    )
            )
        }

        btnbook1.setOnClickListener {

            bookedby1.setValue(bookeduser)
            Toast.makeText(this@MainActivity, "Booked by $bookeduser in slot 1 for 5 minutes", Toast.LENGTH_SHORT).show()
            vacant1.setValue(false)
           val randomNumber1 = (9999..99999).random()
            Toast.makeText(this@MainActivity, "Enter this code in gate $randomNumber1", Toast.LENGTH_SHORT).show()
             getin1.setValue(randomNumber1)

            val handler = Handler()
            handler.postDelayed({
                vacant1.setValue(true)
            }, 8000)
        }

           btnbook2.setOnClickListener {
            bookedby2.setValue(bookeduser)
            Toast.makeText(this@MainActivity, "Booked by $bookeduser in slot 2 for 5 minutes", Toast.LENGTH_SHORT).show()
            vacant2.setValue(false)
            val randomNumber2 = (9999..99999).random()
            Toast.makeText(this@MainActivity, "Enter this code in gate $randomNumber2", Toast.LENGTH_SHORT).show()
            getin2.setValue(randomNumber2)

                        val handler = Handler()
            handler.postDelayed({
                vacant2.setValue(true)
            }, 8000)

        }
        btnbook3.setOnClickListener {
           bookedby3.setValue(bookeduser)
            Toast.makeText(this@MainActivity, "Booked by $bookeduser in slot 3 for 5 minutes", Toast.LENGTH_SHORT).show()
            vacant3.setValue(false)
            val randomNumber3 = (9999..99999).random()
          Toast.makeText(this@MainActivity, "Enter this code in gate $randomNumber3", Toast.LENGTH_SHORT).show()
            getin3.setValue(randomNumber3)


                        val handler = Handler()
            handler.postDelayed({
                vacant3.setValue(true)
            }, 8000)

        }

///////////////////////// for vacant valueevent listener

        vacant1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(Boolean::class.java)
                if (value != null) {

                    if(value==true){
                        Toast.makeText(this@MainActivity, "Vacancy of 1 is empty", Toast.LENGTH_SHORT).show()
                        tvBook1.setText("Empty")
                        //        book1.setValue(false);
                            btnbook1.setVisibility(View.VISIBLE);

                    }else{
                        Toast.makeText(this@MainActivity, "Vacancy of slot 2 is closed", Toast.LENGTH_SHORT).show()
                        //tvSlot1.setText("Empty")
                        tvBook1.setText("Closed")
                        //      book1.setValue(false);
                          btnbook1.setVisibility(View.GONE);
                        //       btnopendoor1.setVisibility(View.VISIBLE);
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Parking value", "Failed to read value.", error.toException())
                Toast.makeText(this@MainActivity, "Failed to read Value", Toast.LENGTH_SHORT).show()
            }
        })

        vacant2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(Boolean::class.java)
                if (value != null) {

                    if(value==true){
                        Toast.makeText(this@MainActivity, "Vacancy of 2 is empty", Toast.LENGTH_SHORT).show()
                        tvBook2.setText("Empty")
                        //        book1.setValue(false);
                            btnbook2.setVisibility(View.VISIBLE);

                    }else{
                        Toast.makeText(this@MainActivity, "Vacancy of slot 2 is closed", Toast.LENGTH_SHORT).show()
                        //tvSlot1.setText("Empty")
                        tvBook2.setText("Closed")
                        //      book1.setValue(false);
                         btnbook2.setVisibility(View.GONE);
                        //       btnopendoor1.setVisibility(View.VISIBLE);
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Parking value", "Failed to read value.", error.toException())
                Toast.makeText(this@MainActivity, "Failed to read Value", Toast.LENGTH_SHORT).show()
            }
        })

        vacant3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(Boolean::class.java)
                if (value != null) {

                    if(value==true){
                        Toast.makeText(this@MainActivity, "Vacancy of 3 is empty", Toast.LENGTH_SHORT).show()
                        tvBook3.setText("Empty")
                        //        book1.setValue(false);
                            btnbook3.setVisibility(View.VISIBLE);

                    }else{
                        Toast.makeText(this@MainActivity, "Vacancy of slot 3 is closed", Toast.LENGTH_SHORT).show()
                        //tvSlot1.setText("Empty")
                        tvBook3.setText("Closed")
                        //      book1.setValue(false);
                          btnbook3.setVisibility(View.GONE);
                        //       btnopendoor1.setVisibility(View.VISIBLE);
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Parking value", "Failed to read value.", error.toException())
                Toast.makeText(this@MainActivity, "Failed to read Value", Toast.LENGTH_SHORT).show()
            }
        })
        /////////////// value event listener for sensor status

        slot1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val values = dataSnapshot.getValue(Boolean::class.java)
                Log.d("Sensor data", "Value is: $values")

                if (values != null) {

                    if(values==true){
                        Toast.makeText(this@MainActivity, "Cuurent parking status in slot 1 is occupied", Toast.LENGTH_SHORT).show()
                        tvSlot1.setText("Occupied")
                        //        book1.setValue(false);
                        btnbook1.setVisibility(View.GONE);



                    }else{
                        Toast.makeText(this@MainActivity, "Cuurent parking status in slot 1 is empty", Toast.LENGTH_SHORT).show()
                        tvSlot1.setText("Empty")
                        //      book1.setValue(false);
                        btnbook1.setVisibility(View.VISIBLE);
                        //       btnopendoor1.setVisibility(View.VISIBLE);
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Parking value", "Failed to read value.", error.toException())
                Toast.makeText(this@MainActivity, "Failed to read Value", Toast.LENGTH_SHORT).show()
            }
        })
        ///for slot 2

        slot2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value2 = dataSnapshot.getValue(Boolean::class.java)
                Log.d("Sensor data", "Value is: $value2")

                if (value2 != null) {

                    if(value2==true){
                        Toast.makeText(this@MainActivity, "Cuurent parking status in slot 2 is occupied", Toast.LENGTH_SHORT).show()
                        tvSlot2.setText("Occupied")
                        //        book1.setValue(false);
                        btnbook2.setVisibility(View.GONE);



                    }else{
                        Toast.makeText(this@MainActivity, "Cuurent parking status in slot 2 is empty", Toast.LENGTH_SHORT).show()
                        tvSlot2.setText("Empty")
                        //      book1.setValue(false);
                        btnbook2.setVisibility(View.VISIBLE);
                        //      btnopendoor2.setVisibility(View.VISIBLE);
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Parking value", "Failed to read value.", error.toException())
                Toast.makeText(this@MainActivity, "Failed to read Value", Toast.LENGTH_SHORT).show()
            }
        })

        //for slot 3

        slot3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value3 = dataSnapshot.getValue(Boolean::class.java)
                Log.d("Sensor data", "Value is: $value3")

                if (value3 != null) {

                    if(value3==true){
                        Toast.makeText(this@MainActivity, "Cuurent parking status in slot 3 is occupied", Toast.LENGTH_SHORT).show()
                        tvSlot3.setText("Occupied")
                        //        book1.setValue(false);
                        btnbook3.setVisibility(View.GONE);



                    }else{
                        Toast.makeText(this@MainActivity, "Cuurent parking status in slot 2 is empty", Toast.LENGTH_SHORT).show()
                        tvSlot3.setText("Empty")
                        //      book1.setValue(false);
                        btnbook3.setVisibility(View.VISIBLE);
                        //    btnopendoor3.setVisibility(View.VISIBLE);
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Parking value", "Failed to read value.", error.toException())
                Toast.makeText(this@MainActivity, "Failed to read Value", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun signOut() {
        auth.signOut()
    }

    private fun calculateTime(){

    }
    private fun cancelBooking(){

    }

//    fun generateRandom(){
//        val randomNumber = (9999..99999).random()
//        return
//    }
}