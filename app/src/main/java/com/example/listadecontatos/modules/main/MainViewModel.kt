package com.example.listadecontatos.modules.main

import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.listadecontatos.model.Contact
import com.example.listadecontatos.model.ContactList
import java.util.Locale

class MainViewModel : ViewModel() {

    private var contactListInstance = ContactList

    fun createContact(contactName: String, contactPhoneNumber: String): Boolean {
        val contactIfAlreadyExist = contactListInstance.verifyIfContactAlreadyExist(contactPhoneNumber)

        contactIfAlreadyExist?.let {
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