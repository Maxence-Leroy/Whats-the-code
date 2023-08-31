package com.ragicorp.whatsthecode

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext

class PermissionsManager(private val activity: Activity) {
    private val permissionStatusChannel = Channel<Map<String, Boolean>>()

    private suspend fun requestPermissions(permissions: Array<String>): Map<String, Boolean> {
        activity.requestPermissions(permissions, 0)
        return withContext(Dispatchers.IO) {
            permissionStatusChannel.receive()
        }
    }

    private fun handlePermissionDenial() {
        AlertDialog.Builder(activity)
            .setTitle(R.string.permissionDeniedDialog_title)
            .setMessage(R.string.permissionDeniedDialog_message)
            .setCancelable(false)
            .setPositiveButton(
                R.string.permissionDeniedDialog_positive,
            ) { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + activity.applicationContext.packageName)
                startActivity(activity, intent, null)
            }
            .setNegativeButton(
                R.string.permissionDeniedDialog_negative
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    suspend fun onPermissionResult(permissionStatusMap: Map<String, Boolean>) {
        withContext(Dispatchers.IO) {
            permissionStatusChannel.send(permissionStatusMap)
        }
    }

    private fun isPermissionGranted(perm: String): Boolean {
        return when (activity.checkSelfPermission(perm)) {
            PackageManager.PERMISSION_GRANTED -> true
            PackageManager.PERMISSION_DENIED -> false
            else -> throw java.lang.Exception("Invalid result for checkPermissionGranted")
        }
    }
}