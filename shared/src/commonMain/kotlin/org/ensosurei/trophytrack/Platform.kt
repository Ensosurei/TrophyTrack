package org.ensosurei.trophytrack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform