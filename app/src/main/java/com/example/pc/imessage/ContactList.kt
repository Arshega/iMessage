package com.example.pc.imessage

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.example.pc.imessage.FirestoreDatabase.firestoreAction
import com.example.pc.imessage.FragmentHolder.Fragment_Contact
import com.example.pc.imessage.FragmentHolder.Fragment_Message
import kotlinx.android.synthetic.main.activity_home.*

class ContactList : AppCompatActivity() {
    var isFragmentOneLoaded = true
    var id = ""
    var fullname = ""
    val bundle = Bundle()
    val manager = supportFragmentManager
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.messaging -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.contacs -> {
            showFragmentParent()

                return@OnNavigationItemSelectedListener true

            }

        }
        false
    }


    var arrofContact :ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val iin = intent
        var b: Bundle = iin.extras

        showFragmentParent()
        bundle.putString("myemail",b.getString("email"))
        bundle.putString("value", b.getString("id"))
        bundle.putString("name",b.getString("name"))
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


    fun showFragmentParent() {

        val transaction = manager.beginTransaction()
        val fragment = Fragment_Contact()
        fragment.arguments = bundle
        transaction.replace(R.id.frag, fragment ,"tagname")
        transaction.addToBackStack("tagname")
        transaction.commit()


    }

    fun showFragmentMessage(){

        val transaction = manager.beginTransaction()
        val fragment = Fragment_Message()
        fragment.arguments = bundle
        transaction.replace(R.id.frag, fragment ,"tagname")
        transaction.addToBackStack("tagname")
        transaction.commit()

    }


}
