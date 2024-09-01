package com.modak.rate_limited_notification_service.domain.models

data class NotificationConfigModel(
    val notificationType: String,
    val rateLimitInterval: Int,
    val rateLimitCount: Int,
)