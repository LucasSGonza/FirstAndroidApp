package com.example.listadecontatos.model

import android.util.Log

object ContactList {

    private var contactList = mutableSetOf<Contact>()
    private var contactListMaxLength: Int = 3

    fun createContact(contact: Contact) {
        Log.i("create", "Name: ${contact.name}, Phone: ${contact.phoneNumber}")
        contactList.add(contact)
    }

    fun getContactListSize(): Int {
        return contactList.size
    }

    fun verifyIfContactAlreadyExist(contactPhoneNumber: String): Contact? {
        return contactList.firstOrNull { contactPhoneNumber == it.phoneNumber }
    }

    fun verifyIfContactListIsFull(): Boolean {
        return contactList.count() == contactListMaxLength
    }

    fun verifyIfContactListIsEmpty(): Boolean {
        return contactList.isEmpty()
    }

    fun clearContactList() {
        contactList.clear()
    }

    fun getContactsInfoTheyExist(): Set<Pair<String, String>>? {
        if (contactList.isNotEmpty()) {
            var setOfContactInfo = mutableSetOf<Pair<String, String>>()
            contactList.forEach {
                setOfContactInfo.add(Pair(it.name, it.phoneNumber))
            }
            return setOfContactInfo
        }
        return emptySet()
    }

}