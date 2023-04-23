package com.ha_remote.clientvm.ui.main.foregroundservice

import android.app.AlarmManager
import android.app.PendingIntent
import android.util.Log
import android.widget.Toast

class alarmManagerAutoRefresh {
    private var m_alarmmanager: AlarmManager? = null
    private var m_pendingIntent: PendingIntent? = null

    fun stopAlert() {
        m_alarmmanager!!.cancel(m_pendingIntent);
        m_alarmmanager = null;
    }
}