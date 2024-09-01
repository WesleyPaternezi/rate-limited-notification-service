package com.modak.rate_limited_notification_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RateLimitedNotificationServiceApplication

fun main(args: Array<String>) {
	runApplication<RateLimitedNotificationServiceApplication>(*args)
}
