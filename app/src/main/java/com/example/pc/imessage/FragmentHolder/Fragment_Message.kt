package com.example.pc.imessage.FragmentHolder

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pc.imessage.Messaging.Account
import com.example.pc.imessage.AdapterHolder.AdapterHolder
import com.example.pc.imessage.R

class Fragment_Message:Fragment (){
    var id = ""
    var adapter: AdapterHolder? = null
    var account = Account()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.messaging, container, false)




        return view
    }
}