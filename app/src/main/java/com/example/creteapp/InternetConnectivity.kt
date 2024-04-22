package com.example.creteapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi

class InternetConnectivity(applicationContext: Context) {


    fun checkinternet(context: Context): Boolean
    {
        val cm = context.getSystemService (Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Network Capabilities of Active Network
        val nc = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.getNetworkCapabilities(cm.activeNetwork)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        var connections =
            cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED
        var connections1 =
            cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
        return connections || connections1
    }
}