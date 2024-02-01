package com.example.listadecontatos.modules.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
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
    private var shouldShowNameTextError: Boolean = true

    private var isPhoneValid: Boolean = false
    private var shouldShowPhoneTextError: Boolean = true

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

        with(binding) {
            bttSaveContact.setOnClickListener {
                createContact()
            }

            bttGoToContactList.setOnClickListener {
                startActivity(Intent(it.context, ContactListActivity::class.java))
            }

            bttClearList.setOnClickListener {
                showConfirmationDialog(
                    R.string.dialog_warning_info,
                    R.string.dialog_clear_list,
                    R.string.dialog_ok,
                    R.string.dialog_cancel,
                    R.drawable.baseline_warning_24,
                    ::clearContactList
                )
            }
        }

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

    private fun shouldEnableTextFields(flag: Boolean) {
        with(binding) {
            nameTextInputLayout.isEnabled = flag
            nameTextInputEditText.isEnabled = flag

            phoneTextInputLayout.isEnabled = flag
            phoneTextInputEditText.isEnabled = flag
        }
    }

    private fun disableEmptyTextError() {
        shouldShowPhoneTextError = false
        shouldShowNameTextError = false
    }

    private fun validateNameText(text: String): Boolean {
        val regex = """^\S+(\s+\S+)*$""".toRegex()
        return regex.matches(text)
    }

    private fun validatePhoneText(text: String): Boolean {
        val regex = """^\d+$""".toRegex()
        return regex.matches(text)
    }

    private fun nameInputListener() {
        with(binding) {
            nameTextInputEditText.doOnTextChanged { text, _, _, _ ->
                text?.let {

                    nameTextInputLayout.error = when {
                        text.isEmpty() && shouldShowNameTextError -> getString(R.string.error_when_name_is_empty)
                        !validateNameText(text.toString()) && shouldShowNameTextError -> getString(R.string.error_when_invalid_name)
                        else -> null
                    }

                    shouldShowNameTextError = true
                    isNameValid = text.isNotEmpty() && (nameTextInputLayout.error == null)
                    handleSaveContactBtn()
                }
            }
        }
    }

    private fun phoneInputListener() {
        with(binding) {
            phoneTextInputEditText.doOnTextChanged { text, _, _, _ ->
                text?.let {

                    //verify error
                    phoneTextInputLayout.error = when {
                        //verify first possible error => if has text and the contact already exist

                        text.isNotEmpty() && myViewModel.verifyIfContactAlreadyExist(text.toString()) -> getString(
                            R.string.error_when_contact_already_exist
                        )

                        text.isEmpty() && shouldShowPhoneTextError -> getString(R.string.error_when_phone_number_is_empty)

                        text.length < 3 && shouldShowPhoneTextError -> getString(R.string.error_when_invalid_phone_number)

                        !validatePhoneText(text.toString()) && shouldShowPhoneTextError -> getString(
                            R.string.error_when_provided_not_only_numbers_in_phone
                        )

                        else -> null
                    }

                    shouldShowPhoneTextError = true
                    isPhoneValid =
                        text.isNotEmpty() && !myViewModel.verifyIfContactAlreadyExist(text.toString()) && (phoneTextInputLayout.error == null)
                    handleSaveContactBtn()
                }
            }
        }
    }

    //bundle
    private fun showConfirmationDialog(
        @StringRes title: Int,
        @StringRes message: Int,
        @StringRes positiveButton: Int,
        @StringRes negativeButton: Int,
        @DrawableRes icon: Int,
        positiveCallback: () -> Unit
    ) {
        AlertDialog.Builder(this)
            .setTitle(getString(title))
            .setMessage(getString(message))
            .setIcon(icon)
            .setPositiveButton(positiveButton) { _, _ ->
                positiveCallback.invoke()
            }
            .setNegativeButton(negativeButton) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun verifyIfTheContactListIsFull() {
        if (myViewModel.verifyIfContactListIsFull()) {
            shouldEnableTextFields(false)
            binding.labelForInformation.text =
                getString(R.string.error_when_contact_list_is_full)
            binding.labelForInformation.visibility = View.VISIBLE
        }
    }

    private fun createContact() {
        val contactName = binding.nameTextInputEditText.text.toString()
        val contactPhoneNumber = binding.phoneTextInputEditText.text.toString()

        val result = myViewModel.createContact(contactName, contactPhoneNumber, this)

        if (result) {
            Toast.makeText(
                this,
                getText(R.string.contact_created_with_success),
                Toast.LENGTH_SHORT
            ).show()

            disableEmptyTextError()
            binding.nameTextInputEditText.text?.clear()
            binding.phoneTextInputEditText.text?.clear()

            handleCleanListBtn()
            //always in the final of the operation, verify if the list is full
            verifyIfTheContactListIsFull()
        }
        //if for some reason the "contact already exist validation" in the phoneInputListener doesn't get
        //this error, in my viewModel i still validate this, and return false with a Toast
    }

    private fun clearContactList() {
        myViewModel.clearContactList()

        Toast.makeText(
            this,
            getString(R.string.contact_list_cleared_with_success),
            Toast.LENGTH_SHORT
        ).show()

        disableEmptyTextError()
        shouldEnableTextFields(true)

        with(binding) {
            labelForInformation.visibility = View.GONE
            nameTextInputEditText.text?.clear()
            phoneTextInputEditText.text?.clear()
        }

        handleCleanListBtn()
    }

}