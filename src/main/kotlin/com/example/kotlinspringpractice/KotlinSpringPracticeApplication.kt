package com.example.kotlinspringpractice

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

private val logger = KotlinLogging.logger {}

@SpringBootApplication
@EnableAspectJAutoProxy  // AOP 활성화
class KotlinSpringPracticeApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringPracticeApplication>(*args)
    logger.info { "==================== Application started ====================" }
}