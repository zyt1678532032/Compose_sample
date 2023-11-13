package com.sues.noteapp

import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        runBlocking {
            val a = async {
                println("I'm computing part of the answer")
                6
            }
            val b = async {
                println("I'm computing another part of the answer")
                7
            }
            println("The answer is ${a.await() * b.await()}")
        }
    }

    suspend fun getForecast(): String {
        delay(1000)
        return "Sunny"
    }

    suspend fun getTemperature(): String {
        delay(1000)
        return "30\u00b0C"
    }
}