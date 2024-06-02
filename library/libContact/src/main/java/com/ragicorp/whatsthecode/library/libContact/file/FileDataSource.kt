package com.ragicorp.whatsthecode.library.libContact.file

import android.content.Context
import com.google.gson.Gson
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.net.URI

class FileDataSource(private val context: Context) {
    private val gson = Gson()
    private val contactsDir: File = File(context.filesDir, "contacts")

    init {
        contactsDir.mkdirs()
    }

    suspend fun exportContactInFile(contact: ContactDomain): Uri? = with(Dispatchers.IO) {
        val id = contact.id.toString()
        val file = File(contactsDir, "$id.json")
        val jsonContent = gson.toJson(contact)
        file.writeText(jsonContent)
        return getUriForFile(context, "com.ragicorp.whatsthecode.fileprovider", file)
    }
}