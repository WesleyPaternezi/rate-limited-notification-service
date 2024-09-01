package com.modak.rate_limited_notification_service.domain.services

import com.modak.rate_limited_notification_service.domain.entities.NotificationConfigEntity
import com.modak.rate_limited_notification_service.domain.entities.NotificationEntity
import com.modak.rate_limited_notification_service.domain.entities.toModel
import com.modak.rate_limited_notification_service.domain.exception.UnprocessableNotification
import com.modak.rate_limited_notification_service.domain.models.NotificationModel
import com.modak.rate_limited_notification_service.domain.repositories.NotificationConfigRepository
import com.modak.rate_limited_notification_service.domain.repositories.NotificationRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val notificationConfigRepository: NotificationConfigRepository,

    ) {

    private fun findNotificationConfig(notificationType: String): NotificationConfigEntity? {
        return notificationConfigRepository.findByNotificationType(notificationType)
    }

    private fun findAllUserNotificationType(userId: String, notificationType: String): List<NotificationModel?> {
        return notificationRepository.findAllByUserIdAndNotificationType(userId, notificationType).map { it.toModel() }
    }

    private fun validateNotificationByRateIntervalAndRateCount(
        listNotification: List<NotificationModel?>,
        notificationConfigModel: NotificationConfigEntity
    ): Boolean {
        val now = LocalDateTime.now()

        val filteredListByInterval = listNotification.filter { notification ->
            Duration.between(notification?.createdAt ?: now, now)
                .toMinutes() <= notificationConfigModel.rateLimitInterval.toLong()
        }

        if (filteredListByInterval.count() >= notificationConfigModel.rateLimitCount) {
            throw UnprocessableNotification("Rate limit exceeded: ${notificationConfigModel.notificationType}, for user: ${listNotification[0]?.userId}")
        }
        return true
    }

    @Transactional
    fun sendAndPersistNotification(notificationModel: NotificationModel) {
        val getNotificationConfig = findNotificationConfig(notificationModel.notificationType)
            ?: throw UnprocessableNotification("Notification config not found: ${notificationModel.notificationType}")

        val getUserNotificationByType =
            findAllUserNotificationType(notificationModel.userId, notificationModel.notificationType)

        validateNotificationByRateIntervalAndRateCount(getUserNotificationByType, getNotificationConfig)

        try {
            notificationRepository.save(
                NotificationEntity(
                    id = null,
                    userId = notificationModel.userId,
                    notificationType = notificationModel.notificationType,
                    contentBody = notificationModel.contentBody,
                    createdAt = notificationModel.createdAt
                )
            )
        } catch (ex: Exception) {
            throw ex
        }
    }


//    TODO implement bulk
//    @Transactional
//    fun saveAndSendAllNotifications(listNotificationModel: List<NotificationModel>): ValidateNotificationListDTO {
//        if (listNotificationModel.isEmpty()) return ValidateNotificationListDTO(emptyList(), emptyList())
//
//        val validateNotificationList: ValidateNotificationListDTO = validateNotificationBatch(listNotificationModel)
//
//        try {
//            notificationRepository.saveAll(
//                validateNotificationList.listNotification.map {
//                    NotificationEntity(
//                        id = null,
//                        userId = it.userId,
//                        notificationType = it.notificationType,
//                        contentBody = it.contentBody,
//                        createdAt = it.createdAt
//                    )
//                }
//            )
//            return validateNotificationList
//        } catch (ex: Exception) {
//            throw ex
//        }
//    }

//    private fun validateNotificationBatch(listNotificationModel: List<NotificationModel>): ValidateNotificationListDTO {
//        val listNotification = mutableListOf<NotificationModel>()
//        val listInvalidNotification = mutableListOf<NotificationModel>()
//
//        listNotificationModel.forEach {
//            val resultValidation = findNotificationConfig(it.notificationType)
//
//            if (resultValidation == null) {
//                listInvalidNotification.add(it)
//            }
//            listNotification.add(it)
//        }
//        return ValidateNotificationListDTO(
//            listNotification = listNotification,
//            listInvalidNotification = listInvalidNotification
//        )
//    }
}