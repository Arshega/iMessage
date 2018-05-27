package com.example.pc.imessage.FirestoreDatabase

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.pc.imessage.ContactList
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Google_Actions {
    private var mAuth: FirebaseAuth? = null

  fun firebaseAuthWithGoogle(acct: GoogleSignInAccount,context: Context) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth?.signInWithCredential(credential)!!.addOnCompleteListener(object : OnCompleteListener<AuthResult>{


            override fun onComplete(task: Task<AuthResult>) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth?.getCurrentUser()
                    val intent: Intent = Intent(context.applicationContext, ContactList::class.java)
                    context.startActivity(intent)

                    Toast.makeText(context.applicationContext, "Login Successful", Toast.LENGTH_LONG).show()
                } else {

                }
            }
        })
    }
}