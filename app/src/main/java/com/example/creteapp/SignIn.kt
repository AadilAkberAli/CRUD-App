package com.example.creteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        val  butid = findViewById<Button>(R.id.buttonid)
        val email = sharepreference.getPrefEmailAddress(this);
        val userid = sharepreference.getPrefUserId(this);
        if(email == null)
        {
            val mainIntent = Intent(this, Login::class.java)
            startActivity(mainIntent)
            deletetable()
            finish()
        }
        else
        {
            val mainIntent = Intent(this, postpage::class.java)
            mainIntent.putExtra("user_id", userid);
            mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            startActivity(mainIntent)
            finish()
        }
        butid.setOnClickListener {
            val mainIntent = Intent(this, Login::class.java)
            startActivity(mainIntent)
            finish()
        }
    }

    private fun deletetable()
    {
        applicationContext.deleteDatabase("Post_Data.db")
    }
}