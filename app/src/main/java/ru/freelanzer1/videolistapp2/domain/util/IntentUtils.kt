package ru.freelanzer1.videolistapp2.domain.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log

/**
 * Created by Puskal Khadka on 3/24/2023.
 */
object IntentUtils {
    fun Context.share(
        type: String = "text/plain",
        title: String = "",
        text: String = ""
    ) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            setType(type)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooserIntent = Intent.createChooser(intent, null)
        startActivity(chooserIntent)
    }

    fun Context.redirectToApp(link: String, type: SocialMediaType) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        try {
            when (type) {
                SocialMediaType.YOUTUBE -> intent.setPackage("com.google.android.youtube")
                SocialMediaType.INSTAGRAM -> intent.setPackage("com.instagram.android")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("share", "redirect fail: ${e.message}")
        }
    }
}

fun Context.openAppSetting() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.fromParts("package", packageName, null)
        }
    startActivity(intent)
}

enum class SocialMediaType {
    INSTAGRAM,
    YOUTUBE
}