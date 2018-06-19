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
import android.text.InputType
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap


class RegisterActivity : AppCompatActivity(), TextWatcher, View.OnClickListener{


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

        tvShowHide1.visibility = View.GONE; tvShowHide2.visibility = View.GONE
        editPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        editConfirmPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        editPassword.addTextChangedListener(this); editConfirmPassword.addTextChangedListener(this)

        tvShowHide1.setOnClickListener(this); tvShowHide2.setOnClickListener(this)

        btnCreate.isEnabled = false

       val db = FirebaseFirestore.getInstance()
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED

        var nuser :HashMap<String,Any> ? = HashMap()

            signIn.setOnClickListener(View.OnClickListener {
                var intent: Intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            })

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


        editEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEdit: Editable) {
                if(!isValidEmail(editEmail.text.toString()))
                    editEmail.error = "Email not valid"
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })


        editPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEdit: Editable) {
                if(editPassword.text.toString().length<6)
                    editPassword.error = "Not strong enough"
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        editConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEdit: Editable) {
                if(editPassword.text.toString()!=editConfirmPassword.text.toString())
                    editConfirmPassword.error = "Password doesn't match"
                else
                    btnCreateAcc.isEnabled=true
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        displayShowHide(editPassword, tvShowHide1)
        displayShowHide(editConfirmPassword, tvShowHide2)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvShowHide1 ->toggleShowHide(editPassword, tvShowHide1)

            else -> toggleShowHide(editConfirmPassword, tvShowHide2)
        }
    }

    private fun displayShowHide(editText: EditText?, textView: TextView?) {
        if (editText?.text!!.isNotEmpty()) textView?.visibility = View.VISIBLE
        else textView?.visibility = View.GONE
    }

    private fun toggleShowHide(editText: EditText?, textView: TextView?) {
        if (textView?.text =="SHOW") {
            textView?.text = getString(R.string.tv_show2)
            editText?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText?.setSelection(editPassword.length())
            textView.text="HIDE"
        }
        else {
            textView?.text = getString(R.string.tv_show)
            editText?.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            editText?.setSelection(editPassword.length())
            textView?.text="SHOW"
        }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) false
        else android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }




}
