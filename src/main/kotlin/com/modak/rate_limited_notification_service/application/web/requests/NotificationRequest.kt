package com.modak.rate_limited_notification_service.application.web.requests

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.modak.rate_limited_notification_service.domain.models.NotificationModel
import org.jetbrains.annotations.NotNull

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class NotificationRequest(
    @field:NotNull("user_id must not be null")
    val userId: String,
    @field:NotNull("notification_type must not be null")
    val notificationType: String,
    val contentBody: String?
)
fun NotificationRequest.toModel() = NotificationModel(
    userId = userId,
    notificationType = notificationType,
    contentBody = contentBody
)