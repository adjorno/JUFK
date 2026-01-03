package com.ifochka.jufk

import com.ifochka.kotrack.AnalyticsManager
import com.ifochka.kotrack.createAnalytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() =
    runBlocking {
        val purple = "\u001B[35m"
        val orange = "\u001B[33m"
        val bold = "\u001B[1m"
        val reset = "\u001B[0m"

        try {
            AnalyticsManager(createAnalytics(BuildKonfig.POSTHOG_API_KEY, this@runBlocking)).initialize()
        } catch (_: Throwable) {
            // do nothing
        }

        println()
        println("$purple$bold  ╦╦ ╦╔═╗╦╔═$reset")
        println("$purple$bold  ║║ ║╠╣ ╠╩╗$reset")
        println("$purple$bold ╚╝╚═╝╚  ╩ ╩$reset")
        println()
        println("$bold  Version: ${BuildKonfig.VERSION_NAME}$reset")
        println()
        println("$bold Just Use Fucking Kotlin$reset")
        println()
        println(" One language. One codebase. Every platform.")
        println()
        println("$orange This exact binary was compiled from Kotlin.$reset")
        println(" No JVM. No runtime. Pure native code.")
        println()
        println(" Platforms you can target with Kotlin:")
        println("   - Android")
        println("   - iOS")
        println("   - Web (WASM)")
        println("   - macOS")
        println("   - Windows")
        println("   - Linux")
        println("   - Server (JVM)")
        println()
        println("$bold Stop hiring 5 teams. Learn Kotlin.$reset")
        println()
        println(" ${purple}justusefuckingkotlin.com$reset")
        println()
    }
