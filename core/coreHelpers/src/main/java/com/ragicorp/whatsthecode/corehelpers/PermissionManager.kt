package com.ragicorp.whatsthecode.corehelpers

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext

class PermissionsManager(private val activity: Activity) {
    private val permissionStatusChannel = Channel<Map<String, Boolean>>()
    private val locationManager =
        activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

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

    suspend fun requestFineLocationPermission(): Boolean {
        return if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            requestFineLocationPermissionToUser()
        } else {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(R.string.permission_location_title)
            builder.setMessage(R.string.permission_location_content)
            builder.setPositiveButton(
                R.string.permission_location_yes
            ) { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }

            builder.setNegativeButton(R.string.permission_location_no, null)
            builder.show()
            return false
        }
    }

    private suspend fun requestFineLocationPermissionToUser(): Boolean {
        if (isFineLocationPermissionGranted()) return true
        // We need to request both permissions
        // cf: https://developer.android.com/training/location/permissions#approximate-request
        val resultMap = requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        val res = resultMap.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
        if (!res) {
            handlePermissionDenial()
        }
        return res
    }

    private fun isFineLocationPermissionGranted(): Boolean {
        return isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}