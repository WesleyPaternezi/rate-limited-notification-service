package com.modak.rate_limited_notification_service.domain.repositories

import com.modak.rate_limited_notification_service.domain.entities.NotificationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository: JpaRepository<NotificationEntity, Long> {
    fun findAllByUserIdAndNotificationType(userId: String, notificationType: String): List<NotificationEntity>
}