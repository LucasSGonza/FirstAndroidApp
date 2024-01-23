package com.example.listadecontatos.modules.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.listadecontatos.model.Contact

class MainViewModel : ViewModel() {

    private var contactList = mutableListOf<Contact>()

    //remove this
    fun getContactList(): MutableList<Contact> {
        return this.contactList
    }

    fun createContact(contactName: String, contactPhoneNumber: String, context: Context) {
        val contactIfAlreadyExist = contactList.any { contactPhoneNumber == it.phoneNumber }

        if (contactIfAlreadyExist) {
            Toast.makeText(context,"error", Toast.LENGTH_LONG).show()
            return
        }

        contactList.add(
            Contact(
                contactList.size,
                contactName,
                contactPhoneNumber
            )
        )
        Toast.makeText(context, "success", Toast.LENGTH_LONG).show()
    }

    fun clearContactList(): Boolean {
        if (contactList.isNotEmpty()) {
            contactList.clear()
            return true
        }
        return false
    }

}