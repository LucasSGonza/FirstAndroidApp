package com.example.listadecontatos.modules.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ActivityMainBinding
import com.example.listadecontatos.modules.contactList.ContactListActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MainViewModel
    private var isNameValid: Boolean = false
    private var isPhoneValid: Boolean = false
    private val isListEmpty: Boolean
        get() = myViewModel.verifyIfContactListIsEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        supportActionBar?.hide()
        setupListeners()
    }

    private fun setupListeners() {
        setupClickListeners()
        setupInputsListeners()
    }

    private fun setupClickListeners() {
        createContact()
        goToContactList()
        clearContactList()
    }

    private fun setupInputsListeners() {
        nameInputListener()
        phoneInputListener()
    }

    private fun handleSaveContactBtn() {
        with(binding.bttSaveContact) {
            isEnabled = isNameValid && isPhoneValid
            setTextColor(if (isEnabled) Color.WHITE else Color.parseColor("gray"))
        }
    }

    private fun handleCleanListBtn() {
        with(binding.bttClearList) {
            isEnabled = !isListEmpty
            setTextColor(if (isEnabled) Color.WHITE else Color.parseColor("gray"))
        }
    }

    private fun shouldEnableFields(flag: Boolean) {
        handleSaveContactBtn()
        with(binding) {
            nameTextInputLayout.isEnabled = flag
            nameTextInputLayout.error = null
            nameTextInputEditText.isEnabled = flag

            phoneTextInputLayout.isEnabled = flag
            phoneTextInputLayout.error = null
            phoneTextInputEditText.isEnabled = flag
        }
    }

    private fun nameInputListener() {
        with(binding) {
            nameTextInputEditText.doOnTextChanged { text, start, before, count ->
                text?.let {
                    nameTextInputLayout.error =
                        if (text.isEmpty()) getString(R.string.error_when_name_is_empty) else null
                    isNameValid = text.isNotEmpty()
                    handleSaveContactBtn()
                }
            }
        }
    }

    private fun phoneInputListener() {
        with(binding) {
            phoneTextInputEditText.doOnTextChanged { text, start, before, count ->
                text?.let {
                    phoneTextInputLayout.error = when {
                        text.isNotEmpty() && myViewModel.verifyIfContactAlreadyExist(text.toString()) -> getString(
                            R.string.error_when_contact_already_exist
                        )

                        text.isNotEmpty() -> null
                        else -> getString(R.string.error_when_phone_number_is_empty)
                    }
                    isPhoneValid =
                        text.isNotEmpty() && !myViewModel.verifyIfContactAlreadyExist(text.toString())
                    handleSaveContactBtn()
                }
            }
        }
    }

    private fun createContact() {
        binding.bttSaveContact.setOnClickListener {
            val contactName = binding.nameTextInputEditText.text.toString()
            val contactPhoneNumber = binding.phoneTextInputEditText.text.toString()
            val result = myViewModel.createContact(contactName, contactPhoneNumber)

            if (result) {
                Toast.makeText(
                    this,
                    getText(R.string.contact_created_with_success),
                    Toast.LENGTH_SHORT
                ).show()

                shouldEnableFields(true)
                binding.nameTextInputEditText.text?.clear()
                binding.phoneTextInputEditText.text?.clear()
                handleCleanListBtn()

                //always in the final of the operation, verify if the list is full
                if (myViewModel.verifyIfContactListIsFull()) {
                    shouldEnableFields(false)
                    binding.labelForInformation.text =
                        getString(R.string.error_when_contact_list_is_full)
                    binding.labelForInformation.visibility = View.VISIBLE
                } else {
                    startActivity(Intent(this, ContactListActivity::class.java))
                }
            }
        }
    }

    private fun clearContactList() {
        binding.bttClearList.setOnClickListener {
            //need to add a Dialog
            myViewModel.clearContactList()
            Toast.makeText(
                this,
                getString(R.string.contact_list_cleared_with_success),
                Toast.LENGTH_SHORT
            ).show()

            shouldEnableFields(true)
            binding.labelForInformation.visibility = View.GONE
            binding.nameTextInputEditText.text?.clear()
            binding.phoneTextInputEditText.text?.clear()
            handleCleanListBtn()

        }
    }

    private fun goToContactList() {
        binding.bttGoToContactList.setOnClickListener {
            startActivity(Intent(this, ContactListActivity::class.java))
        }
    }

}