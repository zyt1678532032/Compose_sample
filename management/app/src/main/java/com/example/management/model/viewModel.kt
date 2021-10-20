package com.example.management.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.management.dao.Group
import com.example.management.service.createManagementDao
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val managementDao = createManagementDao()
    val assetGroups = MutableLiveData<List<String>>(listOf())

    val departments = MutableLiveData<List<String>>(listOf())

    init {
        loadAssetsGroups()
        loadDepartments()
    }

    private fun loadDepartments() {
        viewModelScope.launch {
           val departments = managementDao.getAllDepartments().body()
            departments?.forEach{
                this@MainViewModel.departments.value = this@MainViewModel.departments.value?.plus(it.Name)
            }
        }
    }

    // 部门数据
    private fun loadAssetsGroups() {
        viewModelScope.launch {
            val groups = managementDao.getAllAssetsGroups().body()
            groups?.forEach {
                this@MainViewModel.assetGroups.value = this@MainViewModel.assetGroups.value?.plus(it.Name)
            }
        }
    }

}