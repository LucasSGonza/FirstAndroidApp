package com.example.listadecontatos.modules.main

import androidx.lifecycle.ViewModel
import com.example.listadecontatos.model.Contact
import com.example.listadecontatos.model.ContactList

class MainViewModel : ViewModel() {

    private var contactListInstance = ContactList

    //create a dictionary as a return : flag, Contact? --> also return a Contact for use in the UI
    //change Map<> to Pair<>
    fun createContact(contactName: String, contactPhoneNumber: String): Pair<Boolean, Contact?> {
        val contactIfAlreadyExist = contactListInstance.verifyIfContactAlreadyExist(contactPhoneNumber)

        contactIfAlreadyExist?.let {
            return Pair(false, it)
        }

        val contact = Contact(
            contactListInstance.getContactListSize() + 1,
            contactName,
            contactPhoneNumber
        )

        contactListInstance.createContact(contact)
        return (true to contact)
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