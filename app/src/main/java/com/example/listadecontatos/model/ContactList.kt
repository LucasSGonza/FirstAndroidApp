package com.example.listadecontatos.model

import android.util.Log
import java.lang.IndexOutOfBoundsException

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

//    fun getContactList(): Set<Contact> {
//        return setOf(*contactList.toTypedArray())
//    }

    fun getCopyOfContactList(): Set<Contact> {
        return contactList.toMutableSet()
    }

    fun getContactWithTheIdProvided(id: Int): Contact? {
        return contactList.firstOrNull { it.id == id }
    }

}