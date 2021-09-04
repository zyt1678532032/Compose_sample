package com.example.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.navigation.compose.navArgument
import com.example.library.funcOfLibrary // library
import com.example.test.components.*
import com.example.test.components.theme.WeatherTheme

class MainActivity : AppCompatActivity(){
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Button(onClick = {
                val intent = Intent("com.example.test.BootCompleteReceiver")
                intent.setPackage(packageName)
                sendBroadcast(intent)
            }) {
                Text(
                    text = "发送广播"
                )
            }
//            WeatherTheme {
//                Scaffold(
//                    drawerContent = {
//                        SearchWeatherByAddress()
//                    }
//                ) {
//                    ShowWeatherInfo()
//                    funcOfLibrary()
//                }
//            }
        }
    }
}
