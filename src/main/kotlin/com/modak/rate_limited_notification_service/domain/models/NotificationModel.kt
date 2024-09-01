package com.modak.rate_limited_notification_service.domain.models

import java.time.LocalDateTime

data class NotificationModel(
    val userId: String,
    val notificationType: String,
    val contentBody: String?,
    val createdAt: LocalDateTime = LocalDateTime.now()
)