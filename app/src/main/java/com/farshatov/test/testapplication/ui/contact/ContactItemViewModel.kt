package com.farshatov.test.testapplication.ui.contact

import androidx.databinding.ObservableField
import com.farshatov.test.testapplication.data.Contact

class ContactItemViewModel(val contact: Contact) {

    val name = ObservableField(contact.name)

    val phone = ObservableField(contact.phone)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContactItemViewModel

        if (contact != other.contact) return false

        return true
    }

    override fun hashCode(): Int {
        return contact.hashCode()
    }
}