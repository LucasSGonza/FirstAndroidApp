package com.example.listadecontatos.modules.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupClickListeners()
    }

    private fun setupClickListeners() {
        createContact()
        clearContactList()
    }

    private fun createContact() {
        binding.bttSaveContact.setOnClickListener {

            val contactName = binding.nameEditText.text.toString()
            val contactPhoneNumber = binding.phoneEditText.text.toString()
            //first verifications, still in the View
            when {
                contactName.isEmpty() ->
                    Toast.makeText(
                        this,
                        getString(R.string.toast_when_name_invalid),
                        Toast.LENGTH_SHORT
                    ).show()

                contactPhoneNumber.isEmpty() ->
                    Toast.makeText(
                        this,
                        getString(R.string.toast_when_phone_number_invalid),
                        Toast.LENGTH_SHORT
                    ).show()

                else -> {
                    //then, do the 'logical' verifications, sending the user data to the ViewModel
                    val flag = myViewModel.createContact(contactName, contactPhoneNumber) //returns a Contact?

                    if (flag) {
                        binding.nameEditText.text?.clear()
                        binding.phoneEditText.text?.clear()
                        Toast.makeText(
                            this,
                            getString(R.string.toast_when_contact_created_with_success),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "${getString(R.string.toast_when_contact_already_exist)}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            }

        }

    }

    private fun clearContactList() {

        binding.bttClearList.setOnClickListener {

            if (myViewModel.clearContactList()) {
                Toast.makeText(this, getString(R.string.toast_when_contact_list_cleared_with_success), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, getString(R.string.toast_when_contact_list_already_empty), Toast.LENGTH_SHORT).show()
            }

        }

    }

}