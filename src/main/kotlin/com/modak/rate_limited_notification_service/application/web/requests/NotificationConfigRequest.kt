package com.modak.rate_limited_notification_service.application.web.requests

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.modak.rate_limited_notification_service.domain.models.NotificationConfigModel

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class NotificationConfigRequest(
    val notificationType: String,
    val rateLimitInterval: Int,
    val rateLimitCount: Int
)
fun NotificationConfigRequest.toModel() = NotificationConfigModel(
    notificationType = notificationType,
    rateLimitInterval = rateLimitInterval,
    rateLimitCount = rateLimitCount
)