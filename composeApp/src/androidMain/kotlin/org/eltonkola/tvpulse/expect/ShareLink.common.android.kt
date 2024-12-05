package org.eltonkola.tvpulse.expect

import android.content.Intent
import org.eltonkola.tvpulse.AndroidContext

actual fun shareLink(link: String) {
    val context = AndroidContext.get()
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, link)
    }
    val chooser = Intent.createChooser(intent, "Share link")
    context.startActivity(chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

}