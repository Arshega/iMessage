package com.example.pc.imessage

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

class RegisterActivity : AppCompatActivity(){
    lateinit var ref: DatabaseReference
    lateinit var database: FirebaseDatabase
    private lateinit var userlist: MutableList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val userName:EditText = findViewById(R.id.editUserName)
        val email:EditText = findViewById(R.id.editEmail)
        val phoneNum:EditText = findViewById(R.id.editPhoneNum)
        val pass:EditText = findViewById(R.id.editPassword)
        val ConfirmPass:EditText = findViewById(R.id.editConfirmPassword)
        val btnCreate: Button = findViewById(R.id.btnCreateAcc)

        database = FirebaseDatabase.getInstance()
        ref = database.getReference("User")

        btnCreate.setOnClickListener(View.OnClickListener {
            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    userlist.add(User(userName.toString(), email.toString(), phoneNum.toString(), pass.toString(), ConfirmPass.toString()))
                    Toast.makeText(this@RegisterActivity, "YOU HAVE BEEN REGISTERED!!!!!!", Toast.LENGTH_LONG).show()
                }

            })
        })




    }

}
