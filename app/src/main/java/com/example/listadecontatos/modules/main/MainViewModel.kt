package com.example.listadecontatos.modules.main

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.listadecontatos.model.Contact

class MainViewModel : ViewModel() {

    private var contactList = mutableListOf<Contact>()

    fun createContact(contactName: String, contactPhoneNumber: String): Boolean {
        val contactIfAlreadyExist = contactList.firstOrNull { contactPhoneNumber == it.phoneNumber }
        //se já existir um contato, retorna false
        contactIfAlreadyExist?.let {
            return false
        }
        //se não existir um contato com os dados fornecidos, criar um com eles e retorna true
        contactList.add(
            Contact(
                contactList.size,
                contactName,
                contactPhoneNumber
            )
        )
        return true
    }

    fun clearContactList(): Boolean {
        if (contactList.isNotEmpty()) {
            contactList.clear()
            return true
        }
        return false
    }

}