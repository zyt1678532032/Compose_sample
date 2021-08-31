package com.example.test

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import com.example.test.components.SearchWeatherByAddress
import com.example.test.components.theme.WeatherTheme

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme() {
                SearchWeatherByAddress()
            }
        }
    }
}