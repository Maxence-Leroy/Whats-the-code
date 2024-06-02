package com.ragicorp.whatsthecode.library.libContact.file

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import com.google.gson.Gson
import com.ragicorp.whatsthecode.corehelpers.ActivityProvider
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import kotlinx.coroutines.Dispatchers
import java.io.File

class FileDataSource(private val context: Context, private val activityProvider: ActivityProvider) {
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

    fun shareFile(file: Uri) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, file)
            type = "text/json"
        }
        activityProvider.getActivity().startActivity(Intent.createChooser(shareIntent, null))
    }
}