package com.example.management

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.management.util.ReadImgToBinary2
import kotlinx.android.synthetic.main.layout_activity_main.*
import kotlinx.android.synthetic.main.layout_activity_register.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    val baseUrl = "http://10.0.2.2:5000/session1"
    val client = OkHttpClient()
    val handler = Handler()
    val images = mutableListOf<String>()
    private val imageAdapter = object : RecyclerView.Adapter<ImageHoler>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHoler {
            return ImageHoler(layoutInflater.inflate(R.layout.item_asset_image, parent, false))
        }

        override fun onBindViewHolder(holder: ImageHoler, position: Int) {

        }

        override fun getItemCount(): Int {
            return images.size
        }

    }

    // 注册权限和授权结果
    lateinit var registerForActivityResult: ActivityResultLauncher<Void>
    lateinit var registerPermission: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_register)
        // 1.加载departments
        loadDepartments()
        // 2.加载Locations
        loadLocations()
        // 3.加载Asset Group
        loadAssetGroup()
        // 4.日期选择
        dateChoose()
        // 5.back
        back()
        // 6.选择本地图片
        registerPickImagePermission()
        browseImage()
        // 添加适配器
        (imageRecyclerView as RecyclerView).apply {
            val linearLayoutManager = LinearLayoutManager(this@RegisterActivity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            adapter = imageAdapter
        }
        // 7.提交按钮
        (submit as AppCompatButton).setOnClickListener {
            //
            val an = assetName.text.toString()
            val dl = "1"
            val aq = "1"
            val ap = "1"
            val ad = assetDescription.text.toString()
            val exp = editExpired.text.toString()
            val sn = assetContent.text.toString()
            val img = images.toTypedArray().contentToString()
            addAsset(an, dl, aq, ap, ad, exp, sn, img){
                // 提示
                val string = it.body!!.string()
                val jsonArray = JSONArray(string)
                val jsonObject = jsonArray.getJSONObject(0)
                if (jsonObject.getString("result").equals("Failed")) {
                    // 主线程更新UI
                    handler.post {
                        Toast.makeText(this,jsonObject.getString("reason"),Toast.LENGTH_SHORT)
                            .show()
                    }
                }else{
                    // 跳转主活动
                    finish()
                }

            }
        }
    }

    private fun addAsset(vararg params: String, block: (Response) -> Unit) {
        val url = "${baseUrl}/addAsset"
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val json = "{\"an\":\"${params[0]}\",\"dl\":\"${params[1]}\",\"ag\":\"${params[2]}\",\"ap\":\"${params[3]}\",\"ad\":\"${params[4]}\",\"exp\":\"${params[5]}\",\"sn\":\"${params[6]}\",\"img\":${params[7]}}"
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("addAsset", "失败!")
            }

            override fun onResponse(call: Call, response: Response) {
                block(response)
            }
        })
    }

    // 注册选择本地图片权限
    @SuppressLint("NotifyDataSetChanged")
    private fun registerPickImagePermission() {
        registerForActivityResult = registerForActivityResult(object :
            ActivityResultContract<Void, Uri>() {
            override fun createIntent(context: Context, input: Void?): Intent {
                // 选择本地图片
                return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
                return intent!!.data
            }
        }) {
            if (it != null) {
                // 将图片Uri转化为Base64
                val imagePath = ReadImgToBinary2.getPathFromUri(it, contentResolver)
                val imgToBase64 = ReadImgToBinary2.imgToBase64(imagePath)
                Log.i("imgToBase64", imgToBase64)
                // TODO: 2021/10/9 Image列表增加
                images += imgToBase64
                handler.post {
                    imageAdapter.notifyDataSetChanged()
                }
            }
        }
        registerPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionResult ->
                if (permissionResult) { // 授权通过
                    selectImage()
                } else {
                    Toast.makeText(this, "申请权限失败!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // 浏览本地图片
    private fun browseImage() {
        (browse as AppCompatButton).setOnClickListener {
            // 检查是否授权
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) { //未授权
                getPermission()
            } else {
                selectImage()
            }
        }
    }

    private fun getPermission() {
        // 申请权限
        registerPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun selectImage() {
        registerForActivityResult.launch(null)
    }

    private fun back() {
        (back as AppCompatButton).setOnClickListener {
            finish()
        }
    }

    private fun dateChoose() {
        (dateImage as ImageView).setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this, { view, year, month, day ->
                    calendar.set(year, month, day)
                    val format = SimpleDateFormat("yyyy/MM/dd").format(calendar.time)
                    (editExpired as EditText).setText(format)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun loadAssetGroup() {
        val groups = mutableListOf<String>()
        val url = "${baseUrl}/findAllAssetGroups"
        loadDataByGet(url, client) {
            val string = it.body!!.string()
            val jsonArray = JSONArray(string)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                groups += jsonObject.getString("Name")
            }
            // 主线程更新UI
            handler.post {
                (assetGroup as Spinner).adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, groups)
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
                (locationsSpinner as Spinner).adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, locations)
            }
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
                (departmentsSpinner as Spinner).adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, departments)
            }
        }
    }
}