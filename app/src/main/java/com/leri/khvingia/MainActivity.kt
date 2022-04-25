package com.leri.khvingia

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.leri.khvingia.data.BloodBank
import com.leri.khvingia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
lateinit var datetext:TextView
    private val viewModel: ViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datetext=findViewById(R.id.datetext)
        val datepick = findViewById<Button>(R.id.datepickerbtn)

        datepick.setOnClickListener { view: View? ->
            dayPicker(datetext)
        }
        binding.btnAdd.setOnClickListener {
            if (checkAllFields()) {
                addInDatabase()
                nextActivity()
            }
        }

    }

    private fun nextActivity() {
        val intent = Intent(this, DonationsActivity::class.java)
        startActivity(intent)
    }

    private fun addInDatabase() {

        val userProfile = BloodBank(
            name = binding.txtName.text.toString(),
            bloodGroup = binding.txtBloodGroup.text.toString(),
            phone = binding.txtMobileNumber.text.toString(),
            address = binding.txtLocation.text.toString(),
            lastDonatedDate = binding.datetext.text.toString(),
            weight = Integer.parseInt(binding.txtWeight.text.toString())
        )
        viewModel.insert(userProfile)

    }

    private fun checkAllFields(): Boolean {
        return if (binding.txtWeight.text.toString()
                .isEmpty() || binding.txtBloodGroup.text.toString()
                .isEmpty() || binding.txtBloodGroup.text.toString()
                .isEmpty() || binding.txtLocation.text.toString()
                .isEmpty() || binding.txtMobileNumber.text.toString()
                .isEmpty() || binding.txtName.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu1, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.showdonationss -> {
               nextActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun dayPicker(textView: TextView) {
        val dayPickerDialog = DatePickerDialog(
            this, { _, year, month, dayOfMonth ->
                val result = "$dayOfMonth/${month + 1}/$year"
                textView.text = result
            },
            1990, 0, 1
        )
        dayPickerDialog.show()
    }

}
