package com.farshatov.test.testapplication.ui.contactdetail

import android.provider.ContactsContract
import androidx.databinding.ObservableField
import com.farshatov.test.testapplication.data.Contact

class ContactDetailItemViewModel(phoneString: String) {

    val phone = ObservableField(phoneString)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContactDetailItemViewModel

        if (phone != other.phone) return false

        return true
    }

    override fun hashCode(): Int {
        return phone.hashCode()
    }

}