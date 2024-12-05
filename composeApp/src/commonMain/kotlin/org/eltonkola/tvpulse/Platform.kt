package org.eltonkola.tvpulse

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform