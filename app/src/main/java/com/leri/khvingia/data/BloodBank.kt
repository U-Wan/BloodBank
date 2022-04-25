package com.leri.khvingia.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "donation_table")
@Keep
data class BloodBank(

    @SerializedName("name")
    var name: String,
    @SerializedName("group")
    var bloodGroup: String,
    @SerializedName("date")
    var lastDonatedDate: String,
    @SerializedName("mobile")
    var phone: String,
    @SerializedName("weight")
    var weight: Int,
    @SerializedName("location")
    var address: String,
    @PrimaryKey(autoGenerate = true)
var id: Int = 0,

)
