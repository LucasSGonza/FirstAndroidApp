package com.example.listadecontatos.modules.contactList

import androidx.lifecycle.ViewModel
import com.example.listadecontatos.model.Contact
import com.example.listadecontatos.model.ContactList

class ContactListViewModel: ViewModel() {

    private var contactListInstance = ContactList

    fun getCopyOfContactList(): Set<Contact> {
        return contactListInstance.getCopyOfContactList()
    }

    fun verifyIfContactListIsEmpty(): Boolean {
        return contactListInstance.verifyIfContactListIsEmpty()
    }

}