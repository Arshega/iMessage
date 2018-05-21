package com.example.pc.imessage

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import android.support.design.widget.Snackbar

import com.google.firebase.auth.FirebaseUser

import com.google.firebase.auth.AuthResult
import android.support.annotation.NonNull
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthCredential
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.api.Result
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.security.auth.callback.Callback


class LoginActivity : AppCompatActivity() {
    private var RC_SIGN_IN = 101
    var TAG = "error"

    private var em = ""  // email
    private var pa = ""  /// password
    private var purl = ""  // photo url
    private var pass :EditText? = null
    private var email : EditText? = null
    private var mAuth: FirebaseAuth? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
  this.pass = findViewById(R.id.txtPass)
        this.email = findViewById(R.id.txtEmail)


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance();


   SignIn.setOnClickListener({
signIn()
//firestore_getData()
           })


        create.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this@LoginActivity, RegisterActivity::class.java)

            startActivity(intent)
        })

        google.setOnClickListener(View.OnClickListener {
            val intent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        })
    }

    private fun signIn() {

        val signInIntent = mGoogleSignInClient.getSignInIntent()


        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)

                this.em = account.email!!.toString()
                this.pa = account.account!!.toString()
                this.purl = account.photoUrl!!.toString()
                firestore_addData()
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_LONG).show()
            }

        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth?.getCurrentUser()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth?.signInWithCredential(credential)
                ?.addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = mAuth?.getCurrentUser()
                            val intent: Intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                            Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_LONG).show()
                        } else {

                        }
                    }
                })
    }


    fun firestore_addData(){
var user: HashMap<String,Any> = HashMap()
        val db = FirebaseFirestore.getInstance()
        user.put("Email",em)
        user.put("Photo_Url",purl)
        db.collection("Users")
    }


    fun firestore_getData(){



        val db = FirebaseFirestore.getInstance()
        db.collection("Users")
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>{
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        if(p0.isSuccessful()){
                            for (document in p0.result) {
                                if(document["Email"].toString() == email!!.text.toString() &&
                                        document["Password"].toString() ==  pass!!.text.toString()){

                                    //Intent to  Home
                                    val intent: Intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                    intent.putExtra("email",email!!.text.toString())
                                    intent.putExtra("pass",pass!!.text.toString())



                                }
                            }

                        }
                    }


                })
    }
}
