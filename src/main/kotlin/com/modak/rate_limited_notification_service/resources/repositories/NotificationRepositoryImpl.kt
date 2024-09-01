package com.modak.rate_limited_notification_service.resources.repositories

import com.modak.rate_limited_notification_service.domain.repositories.NotificationRepository

class NotificationRepositoryImpl(
    private val notificationRepository: NotificationRepository
)