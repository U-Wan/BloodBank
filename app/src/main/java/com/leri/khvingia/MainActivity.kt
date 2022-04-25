package com.leri.khvingia

import com.leri.khvingia.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.leri.khvingia.data.BloodBank


class MainActivity : AppCompatActivity() {

    private val viewModel: ViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAdd.setOnClickListener {
            if (checkAllFields()) {
                addInDatabase()
                nextActivity()
            }
        }
        binding.btnSeeBase.setOnClickListener {
            nextActivity()
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
            lastDonatedDate = binding.txtLastDonatedDate.text.toString(),
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

}
