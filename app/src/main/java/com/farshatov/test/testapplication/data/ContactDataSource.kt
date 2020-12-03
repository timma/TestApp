package com.farshatov.test.testapplication.data

import android.provider.ContactsContract




interface ContactDataSource {

    val PROJECTION: Array<String>
        get() = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

    suspend fun getContacts(): List<Contact>

    suspend fun getPhonesContact(id: String): List<String>
}