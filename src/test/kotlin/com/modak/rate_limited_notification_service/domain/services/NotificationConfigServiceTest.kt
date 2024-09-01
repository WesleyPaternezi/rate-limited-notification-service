package com.modak.rate_limited_notification_service.domain.services

import com.modak.rate_limited_notification_service.application.web.requests.NotificationConfigRequest
import com.modak.rate_limited_notification_service.application.web.requests.toModel
import com.modak.rate_limited_notification_service.domain.entities.NotificationConfigEntity
import com.modak.rate_limited_notification_service.domain.models.NotificationConfigModel
import com.modak.rate_limited_notification_service.domain.repositories.NotificationConfigRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class NotificationConfigServiceTest {
    private val notificationConfigRepositoryMockk: NotificationConfigRepository = mockk()

    private val notificationConfigService: NotificationConfigService =
        NotificationConfigService(notificationConfigRepositoryMockk)

    @Test
    fun `should create new notification config`() {
        val notificationConfigRequest = NotificationConfigRequest(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )

        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns null

        every { notificationConfigRepositoryMockk.save(any()) } returns NotificationConfigEntity(
            id = 1,
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )


        val result = notificationConfigService.saveNewNotificationConfig(notificationConfigRequest.toModel())

        assertThat(result).isEqualTo(true)

        verify(exactly = 1) {
            notificationConfigRepositoryMockk.findByNotificationType(any())
            notificationConfigRepositoryMockk.save(any())
        }
    }

    @Test
    fun `should failed create new notification config because notification config already exists`() {
        val notificationConfigRequest = NotificationConfigRequest(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )

        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns NotificationConfigEntity(
            id = 1,
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )

        val result = notificationConfigService.saveNewNotificationConfig(notificationConfigRequest.toModel())

        assertThat(result).isEqualTo(false)

        verify(exactly = 1) {
            notificationConfigRepositoryMockk.findByNotificationType(any())
        }
    }

    @Test
    fun `should get notification config`() {
        val getNotificationType = "test"

        val expectedNotificationType = NotificationConfigModel(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1

        )
        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns NotificationConfigEntity(
            id = 1,
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )


        val result = notificationConfigService.findByNotificationConfigType(getNotificationType)


        assertThat(result).isEqualTo(expectedNotificationType)

        verify(exactly = 1) {
            notificationConfigRepositoryMockk.findByNotificationType(any())
        }
    }

    @Test
    fun `should return null when notification config not found`() {
        val getNotificationType = "test"

        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns null

        val result = notificationConfigService.findByNotificationConfigType(getNotificationType)

        assertThat(result).isNull()

        verify(exactly = 1) {
            notificationConfigRepositoryMockk.findByNotificationType(any())
        }
    }

    @Test
    fun `should update notification config`() {
        val notificationConfigRequest = NotificationConfigRequest(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )
        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns NotificationConfigEntity(
            id = 1,
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )
        every { notificationConfigRepositoryMockk.save(any()) } returns NotificationConfigEntity(
            id = 1,
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )

        val result = notificationConfigService.updateNotificationConfig(notificationConfigRequest.toModel())

        assertThat(result).isEqualTo(true)

        verify(exactly = 1) {
            notificationConfigRepositoryMockk.findByNotificationType(any())
            notificationConfigRepositoryMockk.save(any())
        }
    }

    @Test
    fun `should failed update notification config because notification config not found`() {
        val notificationConfigRequest = NotificationConfigRequest(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )
        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns null

        val result = notificationConfigService.updateNotificationConfig(notificationConfigRequest.toModel())

        assertThat(result).isEqualTo(false)

        verify(exactly = 1) {
            notificationConfigRepositoryMockk.findByNotificationType(any())
        }
    }


    @Test
    fun `should delete notification config`() {
        val notificationConfigRequest = NotificationConfigRequest(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )
        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns NotificationConfigEntity(
            id = 1,
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )
        every { notificationConfigRepositoryMockk.deleteByNotificationType(any()) } just runs

        val result = notificationConfigService.deleteNotificationConfig(notificationConfigRequest.notificationType)

        assertThat(result).isEqualTo(true)

        verify(exactly = 1) {
            notificationConfigRepositoryMockk.findByNotificationType(any())
            notificationConfigRepositoryMockk.deleteByNotificationType(any())
        }
    }

    @Test
    fun `should failed delete notification config because notification config not found`() {
        val notificationConfigRequest = NotificationConfigRequest(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )
        every { notificationConfigRepositoryMockk.findByNotificationType(any()) } returns null

        val result = notificationConfigService.deleteNotificationConfig(notificationConfigRequest.notificationType)

        assertThat(result).isEqualTo(false)

        verify(exactly = 1) {
            notificationConfigRepositoryMockk.findByNotificationType(any())
        }
    }
}