package com.example.pc.imessage.FragmentHolder

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import com.example.pc.imessage.Messaging.Account
import com.example.pc.imessage.AdapterHolder.AdapterHolder
import com.example.pc.imessage.AdapterHolder.MessageAdapter
import com.example.pc.imessage.Contacts
import com.example.pc.imessage.Messaging.TheMessage
import com.example.pc.imessage.R
import com.example.pc.imessage.R.id.search
import com.example.pc.imessage.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*

import kotlinx.android.synthetic.main.contact_list.*
import android.content.ClipData.Item
import android.graphics.Color
import android.widget.TextView
import java.util.Locale.filter
import android.support.v4.view.MenuItemCompat
import java.util.zip.Inflater
import android.view.MenuInflater
import android.text.method.TextKeyListener.clear






class Fragment_Contact : Fragment(), SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var id = ""
    lateinit var inflator: MenuInflater
    var name = ""
    var email = ""
    lateinit var mContext: Context
    var adapter: AdapterHolder? = null
    var con: ArrayList<Contacts> = ArrayList()
    var searchView: SearchView? = null
    var account = Account()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.contact_list, container, false)




        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        id = this.arguments!!.getString("value")
//        email = this.arguments!!.getString("myemail")
//        name = this.arguments!!.getString("name")
        //theid = id
       // if (!id.equals("")) {

            val db = FirebaseFirestore.getInstance()
            db.collection("Users")
                    .orderBy("Username", Query.Direction.DESCENDING)
                    .addSnapshotListener(object : EventListener<QuerySnapshot> {

                        override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                            if (p1 != null) {
                                Log.w("ERROR", "Listen failed.", p1)
                                return


                            }

                            for (doc in p0!!.documents) {
                                if (doc.getString("Email") != email) {
                                    var user = doc.toObject(Contacts::class.java)

                                    user.myid = id
                                    user.myemail = email
                                    user.myname = name
                                    user.userid = doc.id
                                    con.add(user)
                                    adapter = AdapterHolder(con, activity!!.applicationContext)
                                    var layout_manager = LinearLayoutManager(activity!!.applicationContext)
                                    layout_manager.reverseLayout = false
                                    recycler.layoutManager = layout_manager
                                    recycler.setHasFixedSize(true)

                                    recycler.adapter = adapter

                                    adapter!!.notifyDataSetChanged()
                                }
                            }
                        }

                    })


            /*  val db = FirebaseFirestore.getInstance()
            db.collection("Users")
                    .document(id)

                    .get()

                    .addOnCompleteListener(object : OnCompleteListener<DocumentSnapshot> {
                        override fun onComplete(p0: Task<DocumentSnapshot>) {
                            // val packs =p0.result.get("Contact") as Map<String, Any>
                            // val index = packs.
                            if (p0.isSuccessful) {

                                var user = p0.result.toObject(Account::class.java)

                                var con = user.contact
                                var count = 0
                                while (con.size > count) {

                                    con[count].myid = id
                                    con[count].myemail = email

                                    count++
                                }
                                adapter = AdapterHolder(con, activity!!.applicationContext)
                                var layout_manager = LinearLayoutManager(activity!!.applicationContext)
                                recycler.layoutManager = layout_manager
                                recycler.setHasFixedSize(true)
                                recycler.adapter = adapter

                            } else {

                                Toast.makeText(context!!.applicationContext, "Failed to Retrieve Data ", Toast.LENGTH_LONG).show()
                            }
                        }
                    })

*/
        //}


    }
}


