package com.example.listadecontatos.modules.contactList

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listadecontatos.databinding.ActivityContactListBinding
import com.example.listadecontatos.databinding.LayoutForContactsBinding

class ContactListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var myViewModel: ContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myViewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
        shouldAllLayoutsAppear(false)
    }

    override fun onResume() {
        super.onResume()
        setupTheVisibilityOffLayoutsForContacts()
    }

    private fun setupTheVisibilityOffLayoutsForContacts() {
        setupLayoutsBasedOnContactInfo()
//        howManyLayoutShouldAppear(myViewModel.getContactListSize())
    }

    private fun shouldAllLayoutsAppear(flag: Boolean) {
        val visibility = if (flag) View.VISIBLE else View.GONE
        with(binding) {
            layoutContact1.specificLayoutForContact.visibility = visibility
            layoutContact2.specificLayoutForContact.visibility = visibility
            layoutContact3.specificLayoutForContact.visibility = visibility
        }
    }

//    private fun howManyLayoutShouldAppear(numberOfLayoutToAppear: Int) {
//        with(binding) {
//            when (numberOfLayoutToAppear) {
//                0 -> shouldAllLayoutsAppear(false)
//                1 -> layoutContact1.specificLayoutForContact.visibility = View.VISIBLE
//                2 -> layoutContact2.specificLayoutForContact.visibility = View.VISIBLE
//                else -> shouldAllLayoutsAppear(true)
//            }
//        }
//    }

    private fun setupLayoutBasedOnContactInfo(contactInfo: Pair<String, String>, layout: LayoutForContactsBinding) {
        layout.contactName.text = contactInfo.first
        layout.contactPhone.text = contactInfo.second
        layout.specificLayoutForContact.visibility = View.VISIBLE
    }

    private fun setupLayoutsBasedOnContactInfo() {
        val contactsInfo = myViewModel.getFirstContactInfoIfHeExist()

        with(binding) {
            contactsInfo?.let { //if not null, exist at least one contact in the list

                contactsInfo.forEach {
                    Log.i("create2", "Name: ${it.first}, Phone: ${it.second}")
                    setupLayoutBasedOnContactInfo(it, layoutContact1)
                }

//                val firstContactInfo = contactsInfo.first()
//                Log.i("create2", "Name: ${firstContactInfo.first}, Phone: ${firstContactInfo.second}")
//                setupLayoutBasedOnContactInfo(firstContactInfo, layoutContact1)

//                layoutContact1.contactName.text = firstContactInfo.first
//                layoutContact1.contactPhone.text = firstContactInfo.second
            }

        }
    }

}