package com.farshatov.test.testapplication.data.local

import android.R
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import com.farshatov.test.testapplication.data.Contact
import com.farshatov.test.testapplication.data.ContactDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.InputStream


class ContactLocalDataSource(private val context: Context, private val dispatcher: CoroutineDispatcher): ContactDataSource{

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
                val idPhoto = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

                var name: String
                var number: String
                var idString: String
                var  image_uri: String?
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex)
                    number = cursor.getString(numberIndex)
                    idString = cursor.getString(id)
                    number = number.replace(" ", "")
                    image_uri = cursor.getString(idPhoto)
                    if (!mobileNoSet.contains(idString)) {
                         val uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id.toLong())
                        contactList.add(Contact(idString, name, number, image_uri))
                        mobileNoSet.add(idString)
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