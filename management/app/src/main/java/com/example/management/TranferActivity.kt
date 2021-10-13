package com.example.management

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import kotlinx.android.synthetic.main.layout_tranfer_activity.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import java.io.IOException

class TranferActivity : AppCompatActivity() {

    val baseUrl = "http://10.0.2.2:5000/session1"
    val client = OkHttpClient()
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_tranfer_activity)

        (name as TextView).text =  intent.getStringExtra("assetName")
        (currentLocation as TextView).text = intent.getStringExtra("departmentName")
        (SN as TextView).text = intent.getStringExtra("assetSN")
        // 1.加载Destination department
        loadDepartments()
        // 2.加载Destination Location
        loadLocations()
        // 3.back
        back()
        // 4.submit
        (submit as AppCompatButton).setOnClickListener {
            val destination = (destinationSpinner as Spinner).selectedItem as String
            val location = (locationSpinner as Spinner).selectedItem as String
            val newAsset = (newAsset as EditText).text.toString()
            val assetName = (name as TextView).text.toString()
            val currentLocation = (currentLocation as TextView).text.toString()
            val sn = (SN as TextView).text.toString()
            // 提交请求
            tranferAsset(destination,location,newAsset,assetName,currentLocation,sn){
                // 跳转主活动
                finish()
            }
        }
    }

    private fun back() {
        (back as AppCompatButton).setOnClickListener {
            finish()
        }
    }

    private fun loadDepartments() {
        val departments = mutableListOf<String>()
        val url = "${baseUrl}/findDepartments"
        loadDataByGet(url, client) {
            val string = it.body!!.string()
            val jsonArray = JSONArray(string)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                departments += jsonObject.getString("Name")
            }
            // 主线程更新UI
            handler.post {
                (destinationSpinner as Spinner).adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, departments)
            }
        }
    }

    private fun loadLocations() {
        val locations = mutableListOf<String>()
        val url = "${baseUrl}/findAllLocations"
        loadDataByGet(url, client) {
            val string = it.body!!.string()
            val jsonArray = JSONArray(string)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                locations += jsonObject.getString("Name")
            }
            // 主线程更新UI
            handler.post {
                (locationSpinner as Spinner).adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, locations)
            }
        }
    }

    private fun tranferAsset(vararg params: String, block: (Response) -> Unit) {
        val url = "${baseUrl}/editAsset"
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val json = "{\"ID\":\"${params[0]}\",\"an\":\"${params[1]}\",\"ap\":\"${params[2]}\",\"ad\":\"${params[3]}\",\"exp\":\"null\",\"sn\":\"${params[4]}\"，\"img\":[]}"
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("editAsset", "失败!")
            }

            override fun onResponse(call: Call, response: Response) {
                block(response)
            }
        })
    }
}
