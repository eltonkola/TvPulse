package org.eltonkola.tvpulse.expect

interface Platform {
    val name: String
    val shareUrl: String
    val rateUrl: String
}

expect fun getPlatform(): Platform
