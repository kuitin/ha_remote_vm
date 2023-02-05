package com.ha_remote.clientvm.ui.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.util.Log
import android.content.BroadcastReceiver
import androidx.appcompat.app.AppCompatActivity

class MyBroadcastReceiver: BroadcastReceiver() {

    override  fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show()
        Log.d("Alarm Bell", "Alarm just fired")
    }

}