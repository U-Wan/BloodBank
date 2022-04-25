package com.leri.khvingia.data


import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leri.khvingia.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.Executors



@Database(entities = [BloodBank::class], version = 1, exportSchema = false)
abstract class BloodBankDB : RoomDatabase() {
    abstract fun UserProfileDao(): BloodBank_DAO

    companion object {
        @Volatile
        private var instance: BloodBankDB? = null
        private const val TAG = "DonationDB"


        @Synchronized
        fun getInstance(context: Context): BloodBankDB {
            if (instance == null) {
                synchronized(BloodBankDB::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            BloodBankDB::class.java, context.getString(R.string.db_name)
                        )
                            .allowMainThreadQueries()
                            .addCallback(object : Callback() {
                                override fun onOpen(db: SupportSQLiteDatabase) {
                                    super.onOpen(db)
                                    Log.d(TAG, "onOpen: ")
                                }

                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    Log.d(TAG, "onCreate: ")
                                    Executors.newSingleThreadScheduledExecutor()
                                    CoroutineScope(Dispatchers.IO).launch{ fillWithDemoData(context) }
                                }
                            }).build()
                    }
                }
            }
            return instance!!
        }

        @WorkerThread
        private suspend fun fillWithDemoData(context: Context) {
            val dao: BloodBank_DAO = getInstance(context).UserProfileDao()
            val userProfile = loadJsonArray(context)
            try {
                for (i in 0 until userProfile!!.length()) {
                    val item = userProfile.getJSONObject(i)
                    dao.insert(
                        BloodBank(
                            item.getString("name"),
                            item.getString("group"),
                            item.getString("date"),
                            item.getString("mobile"),
                            Integer.valueOf(item.getString("weight")),
                            item.getString("location")
                        )
                    )
                }
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
        }

        private fun loadJsonArray(context: Context): JSONArray? {
            val builder = StringBuilder()
            val inr = context.resources.openRawResource(R.raw.data)
            val reader = BufferedReader(InputStreamReader(inr))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                val json = JSONObject(builder.toString())
                return json.getJSONArray("data")
            } catch (exception: IOException) {
                exception.printStackTrace()
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
            return null
        }
    }
}
