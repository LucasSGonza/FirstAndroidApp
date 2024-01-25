package com.example.listadecontatos.modules.contactList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listadecontatos.databinding.ActivityContactListBinding
import com.example.listadecontatos.modules.main.MainViewModel

class ContactListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var myViewModel: ContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myViewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
        setupView()
    }

    private fun setupView() {

    }

}