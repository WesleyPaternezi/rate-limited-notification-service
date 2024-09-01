package com.modak.rate_limited_notification_service.domain.services

import com.modak.rate_limited_notification_service.domain.entities.NotificationConfigEntity
import com.modak.rate_limited_notification_service.domain.entities.toModel
import com.modak.rate_limited_notification_service.domain.models.NotificationConfigModel
import com.modak.rate_limited_notification_service.domain.repositories.NotificationConfigRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class NotificationConfigService(
    private val notificationConfigRepository: NotificationConfigRepository,
) {

    private fun validateNotificationConfigExist(notificationType: String): NotificationConfigEntity? {
        return notificationConfigRepository.findByNotificationType(notificationType)
    }

    @Transactional
    fun saveNewNotificationConfig(notificationConfigModel: NotificationConfigModel): Boolean {
        val notificationConfigAlreadyExist =
            validateNotificationConfigExist(notificationConfigModel.notificationType)

        if (notificationConfigAlreadyExist != null) {
            return false
        }
        notificationConfigRepository.save(
            NotificationConfigEntity(
                id = null,
                notificationType = notificationConfigModel.notificationType,
                rateLimitInterval = notificationConfigModel.rateLimitInterval,
                rateLimitCount = notificationConfigModel.rateLimitCount
            )
        )
        return true
    }

    fun findByNotificationConfigType(notificationType: String): NotificationConfigModel? {
        return notificationConfigRepository.findByNotificationType(notificationType)?.toModel()
    }

    @Transactional
    fun updateNotificationConfig(notificationConfigModel: NotificationConfigModel): Boolean {
        val existingNotificationConfig =
            validateNotificationConfigExist(notificationConfigModel.notificationType) ?: return false

        existingNotificationConfig.apply {
            rateLimitInterval = notificationConfigModel.rateLimitInterval
            rateLimitCount = notificationConfigModel.rateLimitCount
        }

        notificationConfigRepository.save(existingNotificationConfig)

        return true
    }

    @Transactional
    fun deleteNotificationConfig(notificationType: String): Boolean {
        validateNotificationConfigExist(notificationType) ?: return false
        notificationConfigRepository.deleteByNotificationType(notificationType)
        return true
    }
}