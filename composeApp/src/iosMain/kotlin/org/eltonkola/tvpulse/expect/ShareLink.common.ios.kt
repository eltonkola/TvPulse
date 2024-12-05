package org.eltonkola.tvpulse.expect


import platform.UIKit.*

actual fun shareLink(link: String) {
    val activityViewController = UIActivityViewController(activityItems = listOf(link), applicationActivities = null)
    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
        activityViewController,
        animated = true,
        completion = null
    )
}
