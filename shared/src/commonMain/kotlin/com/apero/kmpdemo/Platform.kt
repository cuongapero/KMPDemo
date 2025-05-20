package com.apero.kmpdemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform