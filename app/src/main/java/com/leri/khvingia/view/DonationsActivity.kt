package com.leri.khvingia.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.leri.khvingia.viewmodels.DonatorAdapter
import com.leri.khvingia.R
import com.leri.khvingia.viewmodels.ViewModel
import com.leri.khvingia.databinding.ActivityDonationBinding


class DonationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonationBinding
    private val viewModel: ViewModel by viewModels()
    private val userProfileAdapter = DonatorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DonationsActivity)
            setHasFixedSize(true)
            adapter = userProfileAdapter
        }
        initialData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.sort_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortByDate -> {
              sortByDate()
                true
            }
            R.id.sortByWeight -> {
                sortByWeight()
                true
            }
            R.id.addnewdonation -> {
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                 true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initialData() {
        viewModel.getPager("default").observe(this) {
            userProfileAdapter.submitData(lifecycle, it)
        }
    }

    private fun sortByWeight() {
        viewModel.getPager("by weight").observe(this) {
            userProfileAdapter.submitData(lifecycle, it)
        }
    }

    private fun sortByDate() {
        viewModel.getPager("by date").observe(this) {
            userProfileAdapter.submitData(lifecycle, it)
        }

    }

}