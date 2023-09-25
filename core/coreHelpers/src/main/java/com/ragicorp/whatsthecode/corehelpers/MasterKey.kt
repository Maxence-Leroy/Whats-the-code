package com.ragicorp.whatsthecode.corehelpers

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.security.SecureRandom

object MasterKey {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val HEX_CHARS = "0123456789ABCDEF".toCharArray()
    private const val sharedPreferencesFile = "encryptedSharedPreferences"
    private const val dbKey = "dbKey"

    private fun ByteArray.toHex(): String {
        val result = StringBuilder()
        forEach {
            val octet = it.toInt()
            val firstIndex = (octet and 0xF0).ushr(4)
            val secondIndex = octet and 0x0F
            result.append(HEX_CHARS[firstIndex])
            result.append(HEX_CHARS[secondIndex])
        }
        return result.toString()
    }

    fun getDBPassword(context: Context): CharArray {
        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            sharedPreferencesFile,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        if (sharedPreferences.contains(dbKey)) {
            val key = sharedPreferences.getString(dbKey, null)
                ?: throw NoSuchFieldException("Key not found")
            return key.toCharArray()
        } else {
            val key = ByteArray(32).apply {
                SecureRandom.getInstanceStrong().nextBytes(this)
            }

            val stringKey = key.toHex()

            with(sharedPreferences.edit()) {
                putString(dbKey, stringKey)
                apply()
            }
            return stringKey.toCharArray()
        }
    }
}