package com.example.listadecontatos.modules.contactList

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ActivityContactListBinding
import com.example.listadecontatos.databinding.LayoutForContactsBinding
import java.lang.IndexOutOfBoundsException

class ContactListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var myViewModel: ContactListViewModel
    private var myLayouts = mutableListOf<LayoutForContactsBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("test","on create")

        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        myViewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
        setupClickListeners()

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

    override fun onResume() {
        super.onResume()
        Log.i("test","on resume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("test","on destroy")
    }

    private fun setupLayouts() {
        myLayouts.addAll(
            listOf(
                binding.layoutContact1,
                binding.layoutContact2,
                binding.layoutContact3
            )
        )
        myLayouts.forEach { it.root.visibility = View.GONE }

        myViewModel.getCopyOfContactList().forEach {
            try {
                myLayouts[it.id].contactName.text = it.name
                myLayouts[it.id].contactPhone.text = it.phoneNumber
                myLayouts[it.id].root.visibility = View.VISIBLE
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