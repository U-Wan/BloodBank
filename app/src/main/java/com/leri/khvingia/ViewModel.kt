package com.leri.khvingia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.leri.khvingia.data.BloodBank
import com.leri.khvingia.data.BloodBank_DAO
import com.leri.khvingia.data.BloodBankDB

import kotlinx.coroutines.launch

class ViewModel(app: Application) : AndroidViewModel(app) {

    private var dao: BloodBank_DAO =
        BloodBankDB.getInstance(app.applicationContext).UserProfileDao()

    fun getPager(query: String) =
        Pager(PagingConfig(pageSize = 10, enablePlaceholders = true, maxSize = 200)) {
            when (query) {
                "by weight" -> dao.getAllByWeight()!!
                "by date" -> dao.getAllByDate()!!
                else -> {
                    dao.getAllByDate()!!
                }
            }
        }.liveData.cachedIn(viewModelScope)

    fun insert(bloodBank: BloodBank) {
        viewModelScope.launch {
            dao.insert(bloodBank)
        }
    }

}
