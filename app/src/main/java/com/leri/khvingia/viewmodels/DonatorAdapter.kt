package com.leri.khvingia.viewmodels

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leri.khvingia.databinding.DonationBinding
import com.leri.khvingia.model.BloodBank

class DonatorAdapter : PagingDataAdapter<BloodBank, DonatorAdapter.UserProfileViewHolder>(
    UserProfileComparator
) {

    inner class UserProfileViewHolder(private val binding: DonationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            binding.txtNameItem.text =
               getItem(absoluteAdapterPosition)?.name

            binding.txtBloodGroupItem.text =
                "Blood Group: ${getItem(absoluteAdapterPosition)?.bloodGroup}"

            binding.txtWeightItem.text = "Weight: ${getItem(absoluteAdapterPosition)?.weight}"

            binding.txtMobileNumberItem.text =
                 getItem(absoluteAdapterPosition)?.phone

            binding.txtLocationItem.text =
                "Location: ${getItem(absoluteAdapterPosition)?.address}"

            binding.txtLastDonatedDateItem.text =
                "Last blood donate date: ${getItem(absoluteAdapterPosition)?.lastDonatedDate}"

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileViewHolder {
        return UserProfileViewHolder(
            DonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserProfileViewHolder, position: Int) {
        holder.bind()
    }



    object UserProfileComparator : DiffUtil.ItemCallback<BloodBank>() {
        override fun areItemsTheSame(oldItem: BloodBank, newItem: BloodBank): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BloodBank, newItem: BloodBank): Boolean {
            return oldItem == newItem
        }
    }
}

