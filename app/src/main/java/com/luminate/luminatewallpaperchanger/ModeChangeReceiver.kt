package com.luminate.luminatewallpaperchanger

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

class ModeChangeReceiver : BroadcastReceiver() {
    @SuppressLint("ResourceType")
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_CONFIGURATION_CHANGED) {
            val configuration = context.resources.configuration
            when (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    Toast.makeText(context, "Light mode activated", Toast.LENGTH_SHORT).show()
                    val sharedPreference = context.getSharedPreferences("bg", Context.MODE_PRIVATE)
                    var bg = sharedPreference.getString("background","pink")
                    if (bg == "pink") {
                        WallpaperManager.getInstance(context).setResource(R.drawable.purple_light)
                    } else if (bg == "red") {
                        WallpaperManager.getInstance(context).setResource(R.drawable.red_light)
                    } else if (bg == "green") {
                        WallpaperManager.getInstance(context).setResource(R.drawable.lime_light)
                    } else if (bg == "blue") {
                        WallpaperManager.getInstance(context).setResource(R.drawable.blue_light)
                    }
                }

                Configuration.UI_MODE_NIGHT_YES -> {
                    Toast.makeText(context, "Dark mode activated", Toast.LENGTH_SHORT).show()
                    val sharedPreference = context.getSharedPreferences("bg", Context.MODE_PRIVATE)
                    var bg = sharedPreference.getString("background","pink")
                    if (bg == "pink") {
                        WallpaperManager.getInstance(context).setResource(R.drawable.purple_dark)
                    } else if (bg == "red") {
                        WallpaperManager.getInstance(context).setResource(R.drawable.red_dark)
                    } else if (bg == "green") {
                        WallpaperManager.getInstance(context).setResource(R.drawable.lime_dark)
                    } else if (bg == "blue") {
                        WallpaperManager.getInstance(context).setResource(R.drawable.blue_dark)
                    }
                }
            }
        }
    }
}