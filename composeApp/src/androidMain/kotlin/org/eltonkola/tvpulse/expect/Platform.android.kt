package org.eltonkola.tvpulse.expect


import android.os.Build

class AndroidPlatform : Platform {
    override val rateUrl: String = "market://details?id=org.eltonkola.bestyou"
    override val shareUrl: String = "https://play.google.com/store/apps/details?id=org.eltonkola.bestyou"
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
