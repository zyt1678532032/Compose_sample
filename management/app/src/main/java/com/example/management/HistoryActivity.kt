package com.example.management

import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_history_activity.*
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject

class HistoryActivity : AppCompatActivity() {

    val baseUrl = "http://10.0.2.2:5000/session1"
    val client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_history_activity)
        // 显示历史信息
        showHistorys()
        // back
        (back as AppCompatButton).setOnClickListener {
            finish()
        }

    }

    private fun showHistorys() {
        val url = "${baseUrl}/findTransfer?id=1"
        val data = mutableListOf<JSONObject>()
        loadDataByGet(url, client) {
            val string = it.body!!.string()
            val jsonArray = JSONArray(string)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                data += jsonObject
            }
        }
        // 适配器
        val historyAdapter = object : RecyclerView.Adapter<HistoryHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
                return HistoryHolder(layoutInflater.inflate(R.layout.item_history,parent,false))
            }

            override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
                holder.relocation.text = data[position].getString("TransferDate")
                holder.center.text = data[position].getString("FromAssetSN")
                holder.yelabuga.text = data[position].getString("ToAssetSN")
            }

            override fun getItemCount(): Int {
                return data.size
            }

        }
        (historys as RecyclerView).apply {
            val linearLayoutManager = LinearLayoutManager(this@HistoryActivity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            adapter = historyAdapter
        }
    }
}