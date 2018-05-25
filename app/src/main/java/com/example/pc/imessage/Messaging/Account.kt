package com.example.pc.imessage.Messaging

import com.example.pc.imessage.Contacts
import java.util.ArrayList

class Account(contact:ArrayList<Contacts>, Email:String, Password:String, PhoneNum:String, Username:String ) {

    var contact = contact
    var Email = Email
    var Password = Password
    var PhoneNum = PhoneNum
    var Username = Username



    constructor() : this(ArrayList<Contacts>(),"","","","")


}