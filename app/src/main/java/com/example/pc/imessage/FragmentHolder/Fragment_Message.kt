package com.example.pc.imessage.FragmentHolder

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pc.imessage.Messaging.Account
import com.example.pc.imessage.AdapterHolder.AdapterHolder
import com.example.pc.imessage.AdapterHolder.InboxAdapter
import com.example.pc.imessage.Contacts
import com.example.pc.imessage.Messaging.TheMessage
import com.example.pc.imessage.R
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.contact_list.*
import kotlinx.android.synthetic.main.inboxfrag.*

class Fragment_Message : Fragment() {
    var id = ""
    var email = ""
    var sender: ArrayList<String> = ArrayList()
    var adapter: InboxAdapter? = null
    var account = Account()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        email = this.arguments!!.getString("myemail")
        id = this.arguments!!.getString("value")
        var db = FirebaseFirestore.getInstance()
        db.collection("Users")
                .document(id)
                .collection("Inbox")
                .orderBy("TimeStamp", Query.Direction.DESCENDING)
                .addSnapshotListener(object : EventListener<QuerySnapshot> {

                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        if (p1 != null) {
                            Log.w("ERROR", "Listen failed.", p1)
                            return


                        }
                        var inbox: ArrayList<TheMessage> = ArrayList()

                        for (doc in p0!!.documents) {

                            var user = doc.toObject(TheMessage::class.java)
                            Toast.makeText(context!!.applicationContext, user.Messages, Toast.LENGTH_SHORT).show()


                            if (sender.isEmpty()) {

                                sender.add(user.Sender)
                                inbox.add(user)

                            } else {
                                var count = 0
                                while (sender.size > count) {

                                    if (inbox[count].Sender == user.Sender) {

                                        inbox[count].countOfMsgs++
                                        inbox[count].Messages = user.Messages
                                        inbox[count].TimeStamp = user.TimeStamp

                                    } else {
                                        inbox.add(user)

                                    }
                                    count++
                                }

                            }



                            adapter = InboxAdapter(inbox, activity!!.applicationContext)
                            var layout_manager = LinearLayoutManager(activity!!.applicationContext)
                            layout_manager.reverseLayout = false
                            if(req !=null) {
                                req.layoutManager = layout_manager
                                req.setHasFixedSize(true)
                                req.adapter = adapter
                                adapter!!.notifyDataSetChanged()
                            }
                        }
                    }
                })


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.inboxfrag, container, false)




        return view
    }
}