package com.example.listadecontatos.modules.contactList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.listadecontatos.databinding.ActivityContactListBinding

class ContactListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityContactListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}