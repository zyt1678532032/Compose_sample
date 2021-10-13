package com.example.management.service

import com.example.management.dao.ManagementDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ManagementService {

    companion object{
        // baseUrl 结尾必须为 /
        private const val baseUrl = "http://10.0.2.2:5000/session1/"
        val service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ManagementDao::class.java)
    }
}