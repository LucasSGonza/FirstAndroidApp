package com.example.listadecontatos.modules.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.listadecontatos.R
import com.example.listadecontatos.model.Contact
import com.example.listadecontatos.model.ContactList

class MainViewModel : ViewModel() {

    private var contactListInstance = ContactList

    fun createContact(
        contactName: String,
        contactPhoneNumber: String,
        context: Context
    ): Boolean {
        val contactIfAlreadyExist =
            contactListInstance.verifyIfContactAlreadyExist(contactPhoneNumber)

        contactIfAlreadyExist?.let {
            Toast.makeText(
                context,
                "${context.getString(R.string.error_when_contact_already_exist)}\n" +
                        "${context.getString(R.string.name_edit_text)}: ${contactIfAlreadyExist.name}\n" +
                        "${context.getString(R.string.phone_edit_text)}: $contactPhoneNumber",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        val contact = Contact(
            contactListInstance.getContactListSize(),
            contactName,
            contactPhoneNumber
        )

        contactListInstance.createContact(contact)
        return true
    }

    fun verifyIfContactAlreadyExist(contactPhoneNumber: String): Boolean {
        return contactListInstance.verifyIfContactAlreadyExist(contactPhoneNumber) != null
    }

    fun verifyIfContactListIsFull(): Boolean {
        return contactListInstance.verifyIfContactListIsFull()
    }

    fun verifyIfContactListIsEmpty(): Boolean {
        return contactListInstance.verifyIfContactListIsEmpty()
    }

    fun clearContactList() {
        contactListInstance.clearContactList()
    }

}