package com.example.test

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * 这样可以在项目的任何位置调用SunnyWeatherApplication来获取context实例对象
 */

class SunnyWeatherApplication : Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "彩云天气TOKEN" // 编译时常量
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}