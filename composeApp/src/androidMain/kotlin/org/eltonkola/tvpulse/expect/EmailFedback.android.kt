package org.eltonkola.tvpulse.expect


import android.content.Intent
import android.net.Uri
import org.eltonkola.tvpulse.AndroidContext

actual fun startEmailFeedback(email: String, subject: String, body: String) {
    val context = AndroidContext.get()
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    val chooserIntent = Intent.createChooser(intent, "Send Feedback").apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(chooserIntent)
}
