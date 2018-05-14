package com.example.pc.imessage

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.content.Context.CONNECTIVITY_SERVICE



class RegisterActivity : AppCompatActivity(){
        lateinit var ref: DatabaseReference
        lateinit var database: FirebaseDatabase
        private var userlist: MutableList<User> = arrayListOf()
        lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val userName:EditText = findViewById(R.id.editUserName)
        val email:EditText = findViewById(R.id.editEmail)
        val phoneNum:EditText = findViewById(R.id.editPhoneNum)
        val pass:EditText = findViewById(R.id.editPassword)
        val ConfirmPass:EditText = findViewById(R.id.editConfirmPassword)
        val btnCreate: Button = findViewById(R.id.btnCreateAcc)
        var connected = false
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        database = FirebaseDatabase.getInstance()
        ref = database.getReference("User")
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED

            btnCreate.setOnClickListener(View.OnClickListener {
                if (connected==true) {
                    userlist.add(User(userName.text.toString(), email.text.toString(), phoneNum.text.toString(), pass.text.toString(), ConfirmPass.text.toString()))
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            ref.child("user3").setValue(userlist)
                            Toast.makeText(this@RegisterActivity, "YOU HAVE BEEN REGISTERED!!!!!!", Toast.LENGTH_LONG).show()
                        }

                    })
                }else
                    Toast.makeText(this@RegisterActivity, "There is no Connection!!!", Toast.LENGTH_LONG).show()
            })





    }




}
