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
import android.content.Intent
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap


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
       val db = FirebaseFirestore.getInstance()
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED

        var nuser :HashMap<String,Any> ? = HashMap()

            btnCreate.setOnClickListener(View.OnClickListener {
                if (connected==true) {
                    user = (User(userName.text.toString(), email.text.toString(), phoneNum.text.toString(), pass.text.toString(), ConfirmPass.text.toString()
                    ,""))
                   var users : MutableList<Contacts> = ArrayList()
                    nuser!!.put("Username" ,user.userName)
                    nuser!!.put("Password" ,user.password)
                    nuser!!.put("Email" ,user.email)
                    nuser!!.put("PhoneNum" ,user.phoneNum)
                    nuser!!.put("contact",users)

                    db.collection("Users").add(nuser)

                }else
                    Toast.makeText(this@RegisterActivity, "There is no Connection!!!", Toast.LENGTH_LONG).show()
                var intent: Intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            })





    }




}
