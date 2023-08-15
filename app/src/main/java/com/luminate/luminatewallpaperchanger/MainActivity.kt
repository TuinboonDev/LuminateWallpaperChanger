package com.luminate.luminatewallpaperchanger

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.luminate.luminatewallpaperchanger.ui.theme.LuminateWallpaperChangerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LuminateWallpaperChangerTheme {
                MainScreen()
            }
        }
    }

}

@SuppressLint("ResourceType")
fun setBg(bg: String, context: Context) {
    val sharedPreference = context.getSharedPreferences("bg", Context.MODE_PRIVATE)
    var editor = sharedPreference.edit()
    editor.putString("background",bg)
    editor.commit()
    val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
        if (bg == "pink") {
            WallpaperManager.getInstance(context).setResource(R.drawable.purple_dark)
        } else if (bg == "red") {
            WallpaperManager.getInstance(context).setResource(R.drawable.red_dark)
        } else if (bg == "green") {
            WallpaperManager.getInstance(context).setResource(R.drawable.lime_dark)
        } else if (bg == "blue") {
            WallpaperManager.getInstance(context).setResource(R.drawable.blue_dark)
        }
    } else {
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
}

@Preview
@Composable
fun MainScreen() {
    val context: Context = LocalContext.current
    val sharedPreference = context.getSharedPreferences("bg", Context.MODE_PRIVATE)
    var bg by remember { mutableStateOf(sharedPreference.getString("background","pink")) }

    val serviceIntent = Intent(context, ModeChangeService::class.java)
    context.startService(serviceIntent)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            Image(
                painterResource(id = R.drawable.purple_light),
                null,
                modifier = Modifier
                    .clickable {
                        setBg("pink", context)
                        bg = "pink"
                    }
            )
            Image(
                painterResource(id = R.drawable.red_light),
                null,
                modifier = Modifier
                    .clickable {
                        setBg("red", context)
                        bg = "red"
                    }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                painterResource(id = R.drawable.lime_light),
                null,
                modifier = Modifier
                    .clickable {
                        setBg("green", context)
                        bg = "green"
                    }
            )
            Image(
                painterResource(id = R.drawable.blue_light),
                null,
                modifier = Modifier
                    .clickable {
                        setBg("blue", context)
                        bg = "blue"
                    }

            )
        }
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Current background: " + bg,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
    }
}