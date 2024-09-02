package com.modak.rate_limited_notification_service.domain.gateway

interface NotificationGateway {
    fun send(userId: String, message: String, notificationType: String)
}