package com.example.pc.imessage.AdapterHolder

import android.content.Context
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.pc.imessage.Messaging.TheMessage
import com.example.pc.imessage.R
import java.text.SimpleDateFormat

class InboxAdapter(list: ArrayList<TheMessage>, context: Context) : RecyclerView.Adapter<InboxAdapter.InboxHolder>() {
    var v: View? = null
    var data = list
    override fun onBindViewHolder(holder: InboxHolder, position: Int) {
   holder.message.text = data[position].Messages

        holder.name.text = data[position].SenderName
        if(data[position].Sender != data[position].myemail) {
            holder.counts.text = data[position].countOfMsgs.toString()
        }
        else{
            holder.counts.visibility = View.GONE

        }
        var time =SimpleDateFormat("HH:mm").format(data[position].TimeStamp)
        holder.timestamp.text = time.toString()

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InboxHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.listininbox, parent, false)
        v = view

        return InboxAdapter.InboxHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class InboxHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var name: TextView
        var message: TextView
        var timestamp: TextView
        var counts: TextView

        init {

            image = itemView.findViewById(R.id.imageView6)
            name = itemView.findViewById(R.id.Sender)
            message = itemView.findViewById(R.id.msg)
            timestamp = itemView.findViewById(R.id.timestamp)
            counts = itemView.findViewById(R.id.numberofmsg)


        }

    }


}