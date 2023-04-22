package com.ha_remote.clientvm.ui.main

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

class MyBroadcastReceiver: BroadcastReceiver() {
    var NOTIFICATION_ID = "notification-id"
    val NOTIFICATION_CHANNEL_ID = "10001"
    var NOTIFICATION = "notification"
    @RequiresApi(Build.VERSION_CODES.O)
    override  fun onReceive(context: Context?, intent: Intent?) {
//        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show()
        //TODO QDE https://flexiple.com/android/android-workmanager-tutorial-getting-started/
        var pcloud = PCloud()
        pcloud.RemoveFilesBeforeDate(3)
//        val notificationManager =
//            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val notification: Notification? = intent!!.getParcelableExtra(NOTIFICATION)
//        val importance = NotificationManager.IMPORTANCE_HIGH
//        val notificationChannel =
//            NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
//        notificationManager.createNotificationChannel(notificationChannel)
//        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
//        notificationManager.notify(id, notification)
//        Log.d("Alarm Bell", "Alarm just fired")
    }

}

//class MyBroadcastReceiver: BroadcastReceiver() {
//    var NOTIFICATION_ID = "notification-id"
//    val NOTIFICATION_CHANNEL_ID = "10001"
//    var NOTIFICATION = "notification"
//
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onReceive(p0: Context?, p1: Intent?) {
//        var pcloud = PCloud()
//        pcloud.RemoveFilesBeforeDate(3)
//    }
//}