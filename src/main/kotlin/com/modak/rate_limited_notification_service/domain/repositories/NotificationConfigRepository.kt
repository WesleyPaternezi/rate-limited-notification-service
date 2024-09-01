package com.modak.rate_limited_notification_service.domain.repositories

import com.modak.rate_limited_notification_service.domain.entities.NotificationConfigEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationConfigRepository: JpaRepository<NotificationConfigEntity, Long> {
    fun findByNotificationType(notificationType: String): NotificationConfigEntity?
    fun deleteByNotificationType(notificationType: String)
}