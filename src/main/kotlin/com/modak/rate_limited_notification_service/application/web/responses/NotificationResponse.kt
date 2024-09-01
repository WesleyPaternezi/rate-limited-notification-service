package com.modak.rate_limited_notification_service.application.web.responses

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class NotificationResponse(
    val userId: String,
    val notificationType: String,
    val contentBody: String?
)