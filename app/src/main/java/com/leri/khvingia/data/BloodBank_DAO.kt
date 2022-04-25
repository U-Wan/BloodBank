package com.leri.khvingia.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BloodBank_DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bloodBank: BloodBank)

    @Query("SELECT * FROM blood_base ORDER BY id ASC")
    fun getAll(): PagingSource<Int, BloodBank>?

    @Query("SELECT * FROM blood_base ORDER BY weight ASC")
    fun getAllByWeight(): PagingSource<Int, BloodBank>?

    @Query("SELECT * FROM blood_base ORDER BY lastDonatedDate ASC")
    fun getAllByDate(): PagingSource<Int, BloodBank>?

}