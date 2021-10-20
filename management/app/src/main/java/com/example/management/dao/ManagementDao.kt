package com.example.management.dao


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ManagementDao {
    @GET("findAllAssetGroups")
    suspend fun getAllAssetsGroups(): Response<List<Group>>

    @GET("findDepartments")
    suspend fun getAllDepartments(): Response<List<Department>>

}
data class Group(
    val ID: Int,
    val Name: String
)

data class Department(
    val ID: Int,
    val Locations: List<Location>,
    val Name: String
)

data class Location(
    val DepartmentLocationID: Int,
    val LocationID: Int,
    val LocationName: String
)


