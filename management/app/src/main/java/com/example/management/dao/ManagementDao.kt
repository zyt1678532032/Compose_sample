package com.example.management.dao


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ManagementDao {
    @GET("findAllAssetGroups")
    suspend fun getAllAssetsGroups(): Response<List<Group>>

}
data class Group(
    val ID: String,
    val Name: String
)