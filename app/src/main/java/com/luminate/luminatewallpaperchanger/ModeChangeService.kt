package com.luminate.luminatewallpaperchanger

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder

class ModeChangeService : Service() {

    private val CHANNEL_ID = "ModeChangeServiceChannel"
    private val NOTIFICATION_ID = 1

    private val modeChangeReceiver = ModeChangeReceiver()

    override fun onCreate() {
        super.onCreate()
        registerModeChangeReceiver()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterModeChangeReceiver()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun registerModeChangeReceiver() {
        val filter = IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED)
        registerReceiver(modeChangeReceiver, filter)
    }

    private fun unregisterModeChangeReceiver() {
        unregisterReceiver(modeChangeReceiver)
    }

    private fun createNotification(): Notification {
        createNotificationChannel()

        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
        } else {
            Notification.Builder(this)
        }

        return notificationBuilder
            .setContentTitle("Mode Change Service")
            .setContentText("Running in the background")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Mode Change Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}