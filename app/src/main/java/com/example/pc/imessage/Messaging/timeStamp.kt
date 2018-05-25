package com.example.pc.imessage.Messaging

import android.view.View

import java.text.SimpleDateFormat
import java.util.*


class timeStamp {

    fun getCurrentTime():Date {
        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("HH:mm:ss")
        val strDate =  calendar.time
return strDate
    }
}