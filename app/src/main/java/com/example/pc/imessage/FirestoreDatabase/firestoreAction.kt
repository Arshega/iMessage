package com.example.pc.imessage.FirestoreDatabase

import android.content.Context
import android.widget.Toast
import com.example.pc.imessage.Messaging.Account
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class firestoreAction {


    fun fetchContact(verifier: String, context: Context): Account {
        var user: Account = Account()
        var users: ArrayList<Account> = ArrayList()
        var userid: ArrayList<String> = ArrayList()
        val db = FirebaseFirestore.getInstance()
        db.collection("Users")
                .document(verifier)

                .get()

                .addOnCompleteListener(object : OnCompleteListener<DocumentSnapshot> {
                    override fun onComplete(p0: Task<DocumentSnapshot>) {
                        // val packs =p0.result.get("Contact") as Map<String, Any>
                        // val index = packs.
                        if (p0.isSuccessful) {

                           user = p0.result.toObject(Account::class.java)
                            users.add(user)

                 } else {

                            Toast.makeText(context.applicationContext, "Failed to Retrieve Data ", Toast.LENGTH_LONG).show()
                        }
                    }
                })




        return user
    }


}