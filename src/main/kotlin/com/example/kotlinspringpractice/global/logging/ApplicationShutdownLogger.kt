package com.example.kotlinspringpractice.global.logging

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class ApplicationShutdownLogger {

    @EventListener
    fun onApplicationEvent(event: ContextClosedEvent) {
        log.info { "==================== Application stopped ====================" }
    }
}