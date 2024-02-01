package com.example.listadecontatos.modules.contactList

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ActivityContactListBinding
import java.lang.IndexOutOfBoundsException
import java.util.Locale

class ContactListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var myViewModel: ContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        myViewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
        setupClickListeners()

        setupView()
    }

    private fun setupView() {
        if (myViewModel.verifyIfContactListIsEmpty()) {
            with(binding) {
                layoutForAllContacts.visibility = View.GONE
                labelForInformation.text = getString(R.string.contact_list_is_empty)
                labelForInformation.visibility = View.VISIBLE
            }
        } else {
            setupLayouts()
        }
    }

    private fun setupLayouts() {
        val myLayouts = with(binding) {
            listOf(
                layoutContact1,
                layoutContact2,
                layoutContact3
            )
        }

        myLayouts.forEach { it.root.visibility = View.GONE }

        myViewModel.getCopyOfContactList().forEach {
            try {
                with(myLayouts[it.id]) {
                    contactName.text = it.name
                    contactPhone.text = "+55 " + PhoneNumberUtils.formatNumber(
                        it.phoneNumber,
                        Locale.getDefault().country
                    )
                    root.visibility = View.VISIBLE
                }
            } catch (error: IndexOutOfBoundsException) {
                Toast.makeText(
                    this,
                    getString(R.string.error_when_loading_contacts_data),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupClickListeners() {
        returnToDashboard()
    }

    private fun returnToDashboard() {
        binding.bttReturnToDashboard.setOnClickListener {
            this.finish()
        }
    }

}