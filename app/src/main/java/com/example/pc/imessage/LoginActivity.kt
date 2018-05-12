package com.example.pc.imessage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        create.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        })
    }
}
