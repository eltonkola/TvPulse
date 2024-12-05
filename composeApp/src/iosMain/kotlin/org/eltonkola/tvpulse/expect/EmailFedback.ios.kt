package org.eltonkola.tvpulse.expect

import platform.Foundation.*
import platform.UIKit.*

actual fun startEmailFeedback(email: String, subject: String, body: String) {
    val mailtoUrl = "mailto:$email?subject=${subject.encodeURL()}&body=${body.encodeURL()}"
    val nsUrl = NSURL.URLWithString(mailtoUrl)
    UIApplication.sharedApplication.openURL(nsUrl)
}

private fun String.encodeURL(): String {
    return this.addingPercentEncodingWithAllowedCharacters(NSCharacterSet.URLQueryAllowedCharacterSet) ?: this
}
