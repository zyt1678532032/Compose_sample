package com.example.management.service

import com.example.management.dao.ManagementDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// baseUrl 结尾必须为 /
const val baseUrl = "http://10.0.2.2:5000/session1/"
// 创建Dao的接口实现
fun createManagementDao(): ManagementDao {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ManagementDao::class.java)
}