package com.example.listadecontatos.model

object ContactList {

    private var contactList = mutableSetOf<Contact>()
    private var contactListMaxLength: Int = 3

    fun createContact(contact: Contact) {
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

}