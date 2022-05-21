package com.leri.khvingia.model

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BloodBank_DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bloodBank: BloodBank)
    @Query("SELECT * FROM donation_table ORDER BY weight ASC")
    fun getbyweight(): PagingSource<Int, BloodBank>?

    @Query("SELECT * FROM donation_table ORDER BY id ASC")
    fun getAll(): PagingSource<Int, BloodBank>?


    @Query("SELECT * FROM donation_table ORDER BY lastDonatedDate ASC")
    fun getbydate(): PagingSource<Int, BloodBank>?

}