package com.example.test

import android.app.AlertDialog
import android.app.IntentService
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.components.*
import com.google.android.material.navigation.NavigationView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                Button(onClick = {
                    startService(Intent(this@MainActivity,Myservice::class.java))
                }) {
                    Text(
                        text = "加载网络图片"
                    )
                }

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

    class Myservice: IntentService("Myservice") {
        override fun onHandleIntent(intent: Intent?) {
            Log.i("Myservice的耗时任务所在的线程", Thread.currentThread().name)
            Thread.sleep(2000)


        }
        override fun onDestroy() {
            super.onDestroy()
            Log.i("Myservice","Myservice已经被销毁!")
        }
    }

}

@Composable
fun App(context: Context) {
    // 导航
    val navController = rememberNavController()
    // 添加路由条目,startDestination:这里的String必须为NavHost路由中的一个route匹配,否则出现错误
    NavHost(navController = navController, startDestination = "LoginActivity") {
        composable("LoginActivity") {
            Login(context = context, navController = navController)
        }
        // 登录成功
        composable("MainActivity") {
            Button(onClick = {
                AlertDialog.Builder(context)
                    .setTitle("退出提示")
                    .setMessage("即将退出登录")
                    .setPositiveButton("Ok") { dialog: DialogInterface, which: Int ->
                        navController.navigate("LoginActivity")
                    }.show()
            }) {
                Text(
                    text = "强制下线"
                )
            }
        }
    }

}

@Composable
fun Login(context: Context, navController: NavController) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = {
                Text(
                    text = "登录名"
                )
            }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = "密码"
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )
        TextButton(onClick = {
            if (username == "zyt" && password == "1234") {
                navController.navigate("MainActivity")
            } else {
                Toast.makeText(context, "密码或者登录输入错误", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(
                text = "登录"
            )
        }
    }
}
