package com.ragicorp.whatsthecode.library.libContact.file

import android.content.Context
import com.google.gson.Gson
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.net.URI

class FileDataSource(private val context: Context) {
    private val gson = Gson()

    suspend fun exportContactInFile(contact: ContactDomain): URI = with(Dispatchers.IO) {
        val id = contact.id.toString()
        val file = File(context.filesDir, "$id.json")
        val jsonContent = gson.toJson(contact)
        file.writeText(jsonContent)
        return file.toURI()
    }
}