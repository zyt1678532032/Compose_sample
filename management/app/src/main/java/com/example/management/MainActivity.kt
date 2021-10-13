package com.example.management

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.management.service.ManagementService
import kotlinx.android.synthetic.main.layout_activity_main.*
import kotlinx.android.synthetic.main.layout_change_assets.*
import kotlinx.coroutines.*
import okhttp3.*
import okio.blackholeSink
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val baseUrl = "http://10.0.2.2:5000/session1"
    private val client = OkHttpClient()
    private val handler = Handler()

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_main)

        GlobalScope.launch {
            test()
        }
        // 加载department Spinner
        loadDepartments()
        // 加载Asset Group Spinner
        // loadAssetGroup()
        // 日期选择
        startDateChoose()
        // 截至日期
        endDateChoose()
        // 点击屏幕失去焦点
        (mainActivity as ConstraintLayout).setOnClickListener {
            (searchEditText as EditText).clearFocus() // 失去焦点
        }
        // 搜索
        (searchEditText as EditText).setOnFocusChangeListener { v, hasFocus: Boolean ->
            if (hasFocus) { // 获取焦点

            } else {// 失去焦点 显示asset List
                val params = arrayOf<String>("", "", "", "")
                showAssetList(*params)
            }
        }

        // 添加按钮
        (add as ImageButton).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // todo: 使用协程加载AssetGroup
    suspend fun test() {
        val response = withContext(Dispatchers.IO) {
            ManagementService.service.getAllAssetsGroups()
        }
        val groups = response.body()
        val data = mutableListOf<String>()
        for (group in groups!!) {
            data += group.Name
        }
        handler.post {
            (spinner2 as Spinner).adapter =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        }
    }

    // 显示Asset List
    @SuppressLint("NotifyDataSetChanged")
    private fun showAssetList(vararg params: String) {
        // 加载网络请求
        val assets = mutableListOf<JSONObject>()
        val assetsAdapter = object : RecyclerView.Adapter<AssetViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
                return AssetViewHolder(layoutInflater.inflate(R.layout.item_asset, parent, false))
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
                holder.assetName.text = assets[position].getString("Name")
                holder.departmentName.text = assets[position].getString("DepartmentName")
                holder.assetSN.text = assets[position].getString("SN")
                // 根据ID查找AssetPhoto
                val id = assets[position].getString("ID")
                getBitmapByID(id) {
                    handler.post {
                        holder.imageView.setImageBitmap(it)
                    }
                }
                // 编辑
                holder.edit1.setOnClickListener {
                    startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
                }
                // 转移历史
                holder.edit2.setOnClickListener {
                    val intent = Intent(this@MainActivity, TranferActivity::class.java)
                    intent.putExtra("assetName", holder.assetName.text)
                    intent.putExtra("departmentName", holder.departmentName.text)
                    intent.putExtra("assetSN", holder.assetSN.text)
                    startActivity(intent)
                }
                // 转移历史
                holder.edit3.setOnClickListener {
                    startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
                }

            }

            override fun getItemCount(): Int {
                return assets.size
            }
        }
        // RecyclerView
        (assetList as RecyclerView).apply {
            // 布局管理
            val linearLayoutManager = LinearLayoutManager(this@MainActivity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            // 设置适配器
            adapter = assetsAdapter
        }
        // 异步获取数据
        Log.i("params", params.contentToString())
        val url =
            "${baseUrl}/findAssets?dp=${params[0]}&ag=${params[1]}&st=${params[2]}&ed=${params[3]}"
        loadDataByGet(url, client) {
            val string = it.body!!.string()
            val jsonArray = JSONArray(string)
            for (i in 0 until jsonArray.length()) {
                assets += jsonArray.getJSONObject(i)
            }
            // 存储数据
            val sharedPreferences = getSharedPreferences("AssetList", Activity.MODE_PRIVATE)
            val edit = sharedPreferences.edit()
            edit.putString("data", assets.toTypedArray().contentToString())
            edit.apply()
            // 数据集变化
            handler.post {
                assetsAdapter.notifyDataSetChanged()
            }
        }


    }

    // 异步回调
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getBitmapByID(id: String, block: (Bitmap) -> Unit) {
        val url = "${baseUrl}/findAssetPhotos?ID=$id"
        loadDataByGet(url, client) {
            val string = it.body!!.string()
            if (string.length > 2) { // 排除 [] 空选项
                val jsonArray = JSONArray(string)
                val jsonObject = jsonArray.getJSONObject(0)
                val base64String = jsonObject.getString("AssetPhoto")
                val byteArray = Base64.getDecoder().decode(base64String)
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                // 回调bitmap
                block(bitmap)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "竖屏", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
            setContentView(R.layout.layout_change_assets)
            Toast.makeText(this, "横屏", Toast.LENGTH_SHORT).show()
            // 获取竖屏的数据
            val sharedPreferences = getSharedPreferences("AssetList", Activity.MODE_PRIVATE)
            val jsonArrayString = sharedPreferences.getString("data", "null")
            Log.i("jsonArrayString", jsonArrayString!!)

            val data = mutableListOf<JSONObject>()
            val jsonArray = JSONArray(jsonArrayString)
            for (i in 0 until jsonArray.length()) {
                data += jsonArray.getJSONObject(i)
            }
            // 创建适配器
            val adapter = object : RecyclerView.Adapter<ChangedAssetHolder>() {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): ChangedAssetHolder {
                    return ChangedAssetHolder(
                        layoutInflater.inflate(
                            R.layout.item_change_asset,
                            parent,
                            false
                        )
                    )
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onBindViewHolder(holder: ChangedAssetHolder, position: Int) {
                    holder.assetName.text = data[position].getString("Name")
                    holder.assetSN.text = data[position].getString("SN")
                    val id = data[position].getString("ID")
                    getBitmapByID(id) {
                        handler.post {
                            holder.imageView.setImageBitmap(it)
                        }
                    }
                }

                override fun getItemCount(): Int {
                    return data.size
                }

            }
            // RecycleView 设置布局管理等配置
            (recyclerView as RecyclerView).apply {
                // 布局管理
                val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                layoutManager = linearLayoutManager
                // 设置适配器
                this.adapter = adapter
            }
        }
    }

    private fun endDateChoose() {
        (pickEndDate as ImageView).setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this, { view, year, month, day ->
                    calendar.set(year, month, day)
                    val format = SimpleDateFormat("yyyy/MM/dd").format(calendar.time)
                    (endDate as EditText).setText(format)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun startDateChoose() {
        (pickStartDate as ImageView).setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this, { view, year, month, day ->
                    calendar.set(year, month, day)
                    val format = SimpleDateFormat("yyyy/MM/dd").format(calendar.time)
                    (startDate as EditText).setText(format)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    // 加载Spinner2 AssetGroup
    private fun loadAssetGroup() {
        val assetGroup = mutableListOf<String>()
        val url = "${baseUrl}/findAllAssetGroups"
        loadDataByGet(url, client) {
            val string = it.body!!.string()
            val jsonArray = JSONArray(string)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                assetGroup += jsonObject.getString("Name")
            }
            // 主线程更新UI
            handler.post {
                (spinner2 as Spinner).adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, assetGroup)
            }
        }
    }

    // 加载Spinner1 Departments
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
                (spinner1 as Spinner).adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, departments)
            }
        }
    }


}

// 加载网络请求
fun AppCompatActivity.loadDataByGet(url: String, client: OkHttpClient, block: (Response) -> Unit) {
    // 请求
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    // call
    val call = client.newCall(request)
    call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("loadDataByGet", "请求失败!!!")
        }

        override fun onResponse(call: Call, response: Response) {
            // 回调 Response
            block(response)
        }
    })
}
