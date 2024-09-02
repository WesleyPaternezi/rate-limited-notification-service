package com.modak.rate_limited_notification_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class RateLimitedNotificationServiceApplication

fun main(args: Array<String>) {
	runApplication<RateLimitedNotificationServiceApplication>(*args)
}
