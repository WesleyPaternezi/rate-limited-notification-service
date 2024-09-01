package com.modak.rate_limited_notification_service.domain.services

import com.modak.rate_limited_notification_service.application.web.requests.NotificationRequest
import com.modak.rate_limited_notification_service.application.web.requests.toModel
import com.modak.rate_limited_notification_service.domain.entities.NotificationConfigEntity
import com.modak.rate_limited_notification_service.domain.entities.NotificationEntity
import com.modak.rate_limited_notification_service.domain.exception.UnprocessableNotificationException
import com.modak.rate_limited_notification_service.domain.repositories.NotificationConfigRepository
import com.modak.rate_limited_notification_service.domain.repositories.NotificationRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class NotificationServiceTest {
    private val notificationRepositoryMockk: NotificationRepository = mockk<NotificationRepository>()
    private val notificationConfigRepositoryMockk: NotificationConfigRepository = mockk<NotificationConfigRepository>()

    private val service: NotificationService =
        NotificationService(notificationRepositoryMockk, notificationConfigRepositoryMockk)

    @Test
    fun `should throws UnprocessableNotification when notification config not found`() {

        val notificationRequest = NotificationRequest(
            userId = "test",
            notificationType = "test",
            contentBody = "test"
        )

        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns null

        assertThrows<UnprocessableNotificationException> {
            service.sendAndPersistNotification(notificationRequest.toModel())
        }

        verify {
            notificationConfigRepositoryMockk.findByNotificationType(any())
        }
    }

    @Test
    fun `should throws UnprocessableNotification when rate limit exceeded`() {

        val notificationRequest = NotificationRequest(
            userId = "test",
            notificationType = "test",
            contentBody = "test"
        )

        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns NotificationConfigEntity(
            notificationType = "test",
            rateLimitCount = 1,
            rateLimitInterval = 1
        )

        every { notificationRepositoryMockk.findAllByUserIdAndNotificationType(any(), any()) } returns listOf(
            NotificationEntity(
                userId = "test",
                notificationType = "test",
                contentBody = "test"
            ),
            NotificationEntity(
                userId = "test",
                notificationType = "test",
                contentBody = "test"
            ),
            NotificationEntity(
                userId = "test",
                notificationType = "test",
                contentBody = "test"
            )
        )

        assertThrows<UnprocessableNotificationException> {
            service.sendAndPersistNotification(notificationRequest.toModel())
        }

        verify {
            notificationConfigRepositoryMockk.findByNotificationType(any())
            notificationRepositoryMockk.findAllByUserIdAndNotificationType(any(), any())
        }
    }

    @Test
    fun `should send a notification to a user`() {
        val notificationRequest = NotificationRequest(
            userId = "test",
            notificationType = "test",
            contentBody = "test"
        )

        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns NotificationConfigEntity(
            notificationType = "test",
            rateLimitCount = 2,
            rateLimitInterval = 2
        )

        every { notificationRepositoryMockk.findAllByUserIdAndNotificationType(any(), any()) } returns listOf(
            NotificationEntity(
                userId = "test",
                notificationType = "test",
                contentBody = "test"
            )
        )

        every { notificationRepositoryMockk.save(any()) } returns NotificationEntity(
            id = 1,
            userId = notificationRequest.userId,
            notificationType = notificationRequest.notificationType,
            contentBody = notificationRequest.contentBody,
            createdAt = LocalDateTime.now()
        )
        service.sendAndPersistNotification(notificationRequest.toModel())

        verify(exactly = 1) {
            notificationConfigRepositoryMockk.findByNotificationType(any())
            notificationRepositoryMockk.findAllByUserIdAndNotificationType(any(), any())
            notificationRepositoryMockk.save(any())
        }
    }

}