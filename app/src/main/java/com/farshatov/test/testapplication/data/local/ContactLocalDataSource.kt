package com.farshatov.test.testapplication.data.local

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import com.farshatov.test.testapplication.data.ContactDataSource
import com.farshatov.test.testapplication.data.Contact
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.InputStream


class ContactLocalDataSource(private val context: Context,private val dispatcher: CoroutineDispatcher): ContactDataSource{

    var contactList = arrayListOf<Contact>()

    override suspend fun getContacts(): List<Contact> = withContext(dispatcher){
        val cr: ContentResolver = context.contentResolver
        val cursor: Cursor? = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        if (cursor != null) {
            val mobileNoSet = HashSet<String>()
            cursor.use { cursor ->
                val nameIndex: Int = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val numberIndex: Int =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val id: Int = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)

                val inputStream: InputStream? = ContactsContract.Contacts.openContactPhotoInputStream(context.contentResolver,
                        ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id.toLong()))

                var name: String
                var number: String
                var idString: String
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex)
                    number = cursor.getString(numberIndex)
                    idString = cursor.getString(id)
                    number = number.replace(" ", "")
                    if (!mobileNoSet.contains(number)) {
                        val photo: Bitmap? = inputStream?.let { BitmapFactory.decodeStream(inputStream) }
                        contactList.add(Contact(idString, name, number, photo))
                        mobileNoSet.add(number)
                        Timber.d(
                                "onCreaterrView  Phone Number: name = $name No = $number"
                        )
                    }
                }
            }
        }
        return@withContext contactList;
    }

    override suspend fun getPhonesContact(id: String): List<String> = withContext(dispatcher) {
        val phonesContact = arrayListOf<String>()
        val cr = context.contentResolver
        val cursor =  cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phonesContact.add(phone);
            }
        }
        cursor?.close();
        return@withContext phonesContact
    }
}