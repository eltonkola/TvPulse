package org.eltonkola.tvpulse.expect


import platform.UIKit.*
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun shareLink(link: String) {
    val activityViewController = UIActivityViewController(activityItems = listOf(link), applicationActivities = null)
    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
        activityViewController,
        animated = true,
        completion = null
    )
}

actual fun openLink(link: String) {
    val url = NSURL(string = link)
    if (url != null && UIApplication.sharedApplication.canOpenURL(url)) {
        UIApplication.sharedApplication.openURL(url)
    }
}
