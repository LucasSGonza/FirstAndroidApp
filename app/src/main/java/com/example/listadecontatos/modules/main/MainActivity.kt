package com.example.listadecontatos.modules.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MainViewModel
    private var isNameValid: Boolean = false
    private var isPhoneValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupClickListeners()
        setupInputsListeners()
        setupVisual()
    }

    private fun setupVisual() {
        binding.bttSaveContact.isEnabled = false
    }

    private fun setupClickListeners() {
        createContact()
        clearContactList()
    }

    private fun setupInputsListeners() {
        nameInputListener()
        phoneInputListener()
    }

    private fun shouldEnableSaveContactBtt() {
        Log.e("testBtt", "isNameValid: $isNameValid, isPhoneValid: $isPhoneValid")
        binding.bttSaveContact.isEnabled = (isNameValid && isPhoneValid)
    }

    private fun enableInputTexts() {
        binding.nameTextInputEditText.isEnabled = true
        binding.phoneTextInputEditText.isEnabled = true
    }

    private fun nameInputListener() {
        binding.nameTextInputEditText.doOnTextChanged { text, start, before, count ->
            text?.let {
                binding.nameTextInputLayout.error =
                    if (text.isEmpty()) getString(R.string.toast_when_name_is_empty) else null
                isNameValid = binding.nameTextInputLayout.error == null
                shouldEnableSaveContactBtt()
            }
        }
    }

    private fun phoneInputListener() {
        binding.phoneTextInputEditText.doOnTextChanged { text, start, before, count ->
            text?.let {
                if (text.isNotEmpty()) {
                    //isso nao deve ser feito aqui => deve ser feito na ViewModel
                    binding.phoneTextInputLayout.error =
                        if (myViewModel.getContactList().any { it.phoneNumber == text.toString() })
                            getString(R.string.toast_when_contact_already_exist) else null
                } else {
                    binding.phoneTextInputLayout.error =
                        getString(R.string.toast_when_phone_number_is_empty)
                }
                isPhoneValid = binding.phoneTextInputLayout.error == null
                shouldEnableSaveContactBtt()
            }
        }
    }

    private fun createContact() {
        binding.bttSaveContact.setOnClickListener {
            val contactName = binding.nameTextInputEditText.text.toString()
            val contactPhoneNumber = binding.phoneTextInputEditText.text.toString()
            myViewModel.createContact(contactName, contactPhoneNumber, this)

            //tirar isso daqui => deve ser feito na ViewModel
            if (myViewModel.getContactList().count() == 3) {
                binding.bttSaveContact.isEnabled = false
                binding.nameTextInputEditText.isEnabled = false
                binding.phoneTextInputEditText.isEnabled = false
                Toast.makeText(
                    this,
                    getString(R.string.toast_when_contact_list_is_full),
                    Toast.LENGTH_LONG
                ).show()
            }
            binding.nameTextInputEditText.text?.clear()
            binding.phoneTextInputEditText.text?.clear()
        }
    }

    private fun clearContactList() {
        binding.bttClearList.setOnClickListener {
            //need to add a Dialog
            if (myViewModel.clearContactList()) {
                Toast.makeText(
                    this,
                    getString(R.string.toast_when_contact_list_cleared_with_success),
                    Toast.LENGTH_SHORT
                ).show()
                enableInputTexts()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.toast_when_contact_list_already_empty),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}