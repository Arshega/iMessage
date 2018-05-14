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
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.security.auth.callback.Callback


class LoginActivity : AppCompatActivity() {
    private var RC_SIGN_IN = 101
    var TAG = "error"
    private var mAuth: FirebaseAuth? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private  var userEmail: String = ""
    private  var userPass: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance();
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("User")
        var email: EditText = findViewById(R.id.txtEmail)
        var pass: EditText = findViewById(R.id.txtPass)
        var userRef = ref.child("user1").child("0")
        SignIn.setOnClickListener(View.OnClickListener {

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    userEmail = p0?.child("email")?.value.toString()
                    userPass = p0?.child("password")?.value.toString()
                    email.text.toString()
                    pass.text.toString()
                    if (userEmail.equals(email)&&userPass.equals(pass)){
                        val intent: Intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }

                }

            })
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
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_LONG).show()
            }

        }
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

}
