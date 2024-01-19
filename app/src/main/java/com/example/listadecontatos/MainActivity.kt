package com.example.listadecontatos

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.listadecontatos.databinding.ActivityMainBinding
import com.example.listadecontatos.model.Contact

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var contactList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        createContact()
        clearContactList()
    }

    private fun createContact() {

        binding.bttSaveContact.setOnClickListener {

            val contactName = binding.nameEditText.text.toString()
            val contactPhoneNumber = binding.phoneEditText.text.toString()

            when {
                contactName.isEmpty() ->
                    Toast.makeText(this, "Por favor, preencha um nome", Toast.LENGTH_SHORT).show()

                contactPhoneNumber.isEmpty() ->
                    Toast.makeText(this, "Por favor, preencha um telefone", Toast.LENGTH_SHORT)
                        .show()

                else -> {

                    contactList.add(
                        Contact(
                            contactList.size,
                            contactName,
                            contactPhoneNumber
                        )
                    )
                    Log.e("lucas", "nome do campo: $contactName")
                    Log.e("lucas", "telefone do campo: $contactPhoneNumber")
                    Log.e("lucas", "nome do objeto na lista: ${contactList.first().name}")
                    binding.nameEditText.text?.clear()
                    binding.phoneEditText.text?.clear()
                    Toast.makeText(this, "Contato criado com sucesso!", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }

    private fun clearContactList() {
        binding.bttClearList.setOnClickListener {
            this.contactList.clear()
            Toast.makeText(this, "Sua lista de contatos foi limpa com sucesso", Toast.LENGTH_SHORT).show()
        }
    }

}