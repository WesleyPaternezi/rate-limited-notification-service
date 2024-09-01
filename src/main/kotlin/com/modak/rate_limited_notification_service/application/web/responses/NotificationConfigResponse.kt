package com.modak.rate_limited_notification_service.application.web.responses

import com.modak.rate_limited_notification_service.domain.models.NotificationConfigModel

data class NotificationConfigResponse(
    val notificationType: String,
    val rateLimitInterval: Int,
    val rateLimitCount: Int
)

fun NotificationConfigModel.toResponse() = NotificationConfigResponse(
    notificationType = this.notificationType,
    rateLimitInterval = this.rateLimitInterval,
    rateLimitCount = this.rateLimitCount
)