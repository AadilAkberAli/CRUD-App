package com.example.creteapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

class Login : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val  butid = findViewById<Button>(R.id.loginid)
        val userid = findViewById<TextInputLayout>(R.id.textInputLayoutid)
        val usetext = findViewById<TextInputEditText>(R.id.usernameid)
        val nameid = findViewById<TextInputLayout>(R.id.textInputLayoutnameid)
        val namess = findViewById<TextInputEditText>(R.id.nameid)

        butid.setOnClickListener {
            if(!isValidEmail(usetext.text.toString()))
            {
                userid.error = "Invalid Email Address"
            }
            else
            {
                userid.error = null
            }

            if(!isnamevalid(namess.text.toString()))
            {
                nameid.error = "Invalid User Name"
            }
            else
            {
                nameid.error = null
            }
            if(userid.error == null && nameid.error == null)
            {

                val check = InternetConnectivity(applicationContext)
                if(check.checkinternet(this))
                {
                    getusernameandemail(this)
                }
                else
                {
                    Toast.makeText(applicationContext,
                        "Check your internet connection",
                        Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    fun isnamevalid(s: String): Boolean {

        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number for which we create an object of
        // Pattern class
        val p: Pattern = Pattern.compile(
            "^([ \\u00c0-\\u01ffa-zA-Z'\\-])+\$"
        )

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression by creating an object of
        // Matcher class
        val m: Matcher = p.matcher(s)

        // Returns boolean value
        return m.matches()
    }


    fun isValidEmail(s: String): Boolean {

        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number for which we create an object of
        // Pattern class
        val p: Pattern = Pattern.compile(
            "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
        )

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression by creating an object of
        // Matcher class
        val m: Matcher = p.matcher(s)

        // Returns boolean value
        return m.matches()
    }

    fun isValidpassword(s: String): Boolean {

        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number for which we create an object of
        // Pattern class
        val p: Pattern = Pattern.compile(
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$"
        )

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression by creating an object of
        // Matcher class
        val m: Matcher = p.matcher(s)

        // Returns boolean value
        return m.matches()
    }

    var checklogin = false
    var userids = 0
    fun getusernameandemail(appCompatActivity: AppCompatActivity)
    {
        val usetext = findViewById<TextInputEditText>(R.id.usernameid)
        val namess = findViewById<TextInputEditText>(R.id.nameid)
        val call = RetrofitClient.getInstance().myApi.allResponse("Bearer a24969d8efe8fb877ff52802e48a129b531291e80163f35597e996d315a826b8")
        call.enqueue(object : Callback<List<users>> {
            override fun onResponse(call: Call<List<users>>, response: Response<List<users>>) {
                for (i in response.body()!!) {
//                    Log.d("USERNAME", namess.text.toString())
//                    Log.d("username", i.username)
//                    Log.d("EMAIL", usetext.text.toString())
//                    Log.d("email", i.email)
//                    Log.d("MATCH", "SUCCESSFULL")
//                    Log.d("Username", i.username)
//                    Log.d("email", i.email)
//                    Log.d("userid", i.userId.toString())
                    if(i.username == namess.text.toString() && i.email == usetext.text.toString())
                    {
                        val remcheck = findViewById<CheckBox>(R.id.remembercheck)
                        if(remcheck.isChecked)
                        {
                            sharepreference.setuserdetails(appCompatActivity, i.username, i.email, i.id)
                        }
                        Log.d("MATCH", "SUCCESSFULL")
                        Log.d("Username", i.username)
                        Log.d("email", i.email)
                        Log.d("userid", i.id.toString())
                        userids = i.id
                        checklogin = true
                        break
                    }
                    else
                    {
                        checklogin = false
                    }

                }
                onsuccess()
            }

            override fun onFailure(call: Call<List<users>>, throwable: Throwable) {
                checklogin = false
                Toast.makeText(applicationContext, "Some thing went wrong", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun onsuccess()
    {
        if(checklogin)
        {
            Log.d("TESTER", "HELLO WORLD")
            Toast.makeText(applicationContext, "Login successfully", Toast.LENGTH_LONG).show()
            val mainIntent = Intent(this, postpage::class.java)
            mainIntent.putExtra("user_id", userids)
            mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(mainIntent)
        }
        else
        {
            Log.d("TEST", userids.toString())
            Toast.makeText(applicationContext, "Username and Email address not match", Toast.LENGTH_LONG).show()
        }
    }
}