package com.example.listadecontatos.modules.contactList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.listadecontatos.databinding.ActivityContactListBinding
import com.example.listadecontatos.databinding.LayoutForContactsBinding
import com.example.listadecontatos.model.Contact

class ContactListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var myViewModel: ContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layouts = listOf(binding.layoutContact1, binding.layoutContact2, binding.layoutContact3)

        myViewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
        layouts.forEach { it.root.visibility = View.GONE }

        myViewModel.getCopyOfContactList().forEachIndexed { index, contact ->
            layouts[index].apply {
                this.contactName.text = contact.name
                this.contactPhone.text = contact.phoneNumber
                this.root.isVisible = true
            }
        }

//        shouldShowAllLayouts(false)
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
//        setupLayouts()
    }

//    private fun setupLayouts() {
//        if (!myViewModel.verifyIfContactListIsEmpty()) {
//            shouldShowAllLayouts(false)
//            myViewModel.getCopyOfContactList().forEach {
//
//                setupAndShowLayout(it, layout)
//            }
//        } else {
//            shouldShowAllLayouts(false)
//        }
//    }

//    private fun setupAndShowLayout(contact: Contact, layout: LayoutForContactsBinding) {
//        layout.contactName.text = contact.name
//        layout.contactPhone.text = contact.phoneNumber
//        layout.mainLayoutForContact.visibility = View.VISIBLE
//    }
//
//    private fun shouldShowAllLayouts(flag: Boolean) {
//        val visibility = if (flag) View.VISIBLE else View.GONE
//        with(binding) {
//            layoutContact1.mainLayoutForContact.visibility = visibility
//            layoutContact2.mainLayoutForContact.visibility = visibility
//            layoutContact3.mainLayoutForContact.visibility = visibility
//        }
//    }

    private fun setupClickListeners() {
        returnToDashboard()
    }

    private fun returnToDashboard() {
        binding.bttReturnToDashboard.setOnClickListener {
            this.finish()
        }
    }

}