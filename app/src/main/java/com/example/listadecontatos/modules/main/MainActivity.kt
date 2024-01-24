package com.example.listadecontatos.modules.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private val isNameValid: Boolean
        get() = binding.nameTextInputLayout.error == null
    private val isPhoneValid: Boolean
        get() = binding.phoneTextInputLayout.error == null
    private val isListEmpty: Boolean
        get() = myViewModel.verifyIfContactListIsEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myViewModel = ViewModelProvider(this)[MainViewModel::class.java]

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
        binding.bttSaveContact.isEnabled = isNameValid && isPhoneValid
    }

    private fun handleCleanListBtn() {
        binding.bttClearList.isEnabled = !isListEmpty
    }

    private fun shouldEnableFields(flag: Boolean) {
        handleSaveContactBtn()
        with(binding) {
            nameTextInputEditText.isEnabled = flag
            phoneTextInputEditText.isEnabled = flag
            nameTextInputLayout.isEnabled = flag
            phoneTextInputLayout.isEnabled = flag
        }
    }

    private fun nameInputListener() {
        with(binding) {
            nameTextInputEditText.doOnTextChanged { text, start, before, count ->
                text?.let {
                    nameTextInputLayout.error =
                        if (text.isEmpty()) getString(R.string.toast_when_name_is_empty) else null
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
                            R.string.toast_when_contact_already_exist
                        )

                        text.isNotEmpty() -> null
                        else -> getString(R.string.toast_when_phone_number_is_empty)
                    }
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

            //search inside the Pair for 'false' flags. If it's false, the contact already exist in the contactList
            if (!result.first) {
                Toast.makeText(
                    this,
                    getString(R.string.toast_when_contact_already_exist),
                    Toast.LENGTH_SHORT
                ).show()
            }
            //if the Pair does not contains 'false', its because the operation was a success!
            else {
                Toast.makeText(
                    this,
                    getText(R.string.toast_when_contact_created_with_success),
                    Toast.LENGTH_SHORT
                ).show()
                binding.nameTextInputEditText.text?.clear()
                binding.phoneTextInputEditText.text?.clear()
                handleCleanListBtn()
            }
            //always in the final of the operation, verify if the list is full
            if (myViewModel.verifyIfContactListIsFull()) {
                binding.labelForInformation.hint =
                    getString(R.string.toast_when_contact_list_is_full)
                binding.labelForInformation.visibility = View.VISIBLE
                shouldEnableFields(false)
            }
        }
    }

    private fun clearContactList() {
        binding.bttClearList.setOnClickListener {
            //need to add a Dialog
            myViewModel.clearContactList()
            Toast.makeText(
                this,
                getString(R.string.toast_when_contact_list_cleared_with_success),
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