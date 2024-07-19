package com.example.kotlinspringpractice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy  // AOP 활성화
class KotlinSpringPracticeApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringPracticeApplication>(*args)
}
