package com.example.listadecontatos.modules.contactList

import androidx.lifecycle.ViewModel
import com.example.listadecontatos.model.ContactList

class ContactListViewModel: ViewModel() {

    private var contactListInstance = ContactList

    fun getContactListSize(): Int {
        return contactListInstance.getContactListSize()
    }

    fun getFirstContactInfoIfHeExist(): Set<Pair<String, String>>? {
        return contactListInstance.getContactsInfoTheyExist()
    }

}