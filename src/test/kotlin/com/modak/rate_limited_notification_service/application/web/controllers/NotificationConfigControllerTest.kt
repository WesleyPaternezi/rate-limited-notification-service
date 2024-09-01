package com.modak.rate_limited_notification_service.application.web.controllers

import com.modak.rate_limited_notification_service.application.web.requests.NotificationConfigRequest
import com.modak.rate_limited_notification_service.application.web.responses.NotificationConfigResponse
import com.modak.rate_limited_notification_service.application.web.responses.StatusResponse
import com.modak.rate_limited_notification_service.domain.models.NotificationConfigModel
import com.modak.rate_limited_notification_service.domain.services.NotificationConfigService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI

class NotificationConfigControllerTest {
    private val notificationConfigServiceMockk: NotificationConfigService = mockk<NotificationConfigService>()

    private val controller: NotificationConfigController = NotificationConfigController(notificationConfigServiceMockk)

    @Test
    fun `should create new notification config`() {
        val notificationConfigRequest = NotificationConfigRequest(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )
        every { notificationConfigServiceMockk.saveNewNotificationConfig(any()) } returns true

        val result = controller.createNotificationConfig(notificationConfigRequest)

        assertThat(result).isEqualTo(
            ResponseEntity.created(URI("/notification-config")).body(
                StatusResponse("Notification config created successfully: test")
            )
        )

        verify(exactly = 1) {
            notificationConfigServiceMockk.saveNewNotificationConfig(any())
        }
    }

    @Test
    fun `should failed create new notification config because notification config already exists`() {
        val notificationConfigRequest = NotificationConfigRequest(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )
        every { notificationConfigServiceMockk.saveNewNotificationConfig(any()) } returns false

        val result = controller.createNotificationConfig(notificationConfigRequest)

        assertThat(result).isEqualTo(
            ResponseEntity.unprocessableEntity().body(
                StatusResponse("Notification config already exists: test")
            )
        )

        verify(exactly = 1) {
            notificationConfigServiceMockk.saveNewNotificationConfig(any())
        }
    }

    @Test
    fun `should get notification config`() {
        val getNotificationType = "test"
        every { notificationConfigServiceMockk.findByNotificationConfigType(any()) } returns NotificationConfigModel(
            notificationType = getNotificationType,
            rateLimitInterval = 1,
            rateLimitCount = 1
        )

        val result = controller.getNotificationConfig(getNotificationType)

        assertThat(result).isEqualTo(
            ResponseEntity.ok().body(
                NotificationConfigResponse(
                    notificationType = getNotificationType,
                    rateLimitInterval = 1,
                    rateLimitCount = 1
                )
            )
        )

        verify(exactly = 1) {
            notificationConfigServiceMockk.findByNotificationConfigType(any())
        }
    }

    @Test
    fun `should failed get notification config because notification config not found`() {
        val getNotificationType = "test"
        every { notificationConfigServiceMockk.findByNotificationConfigType(any()) } returns null

        val result = controller.getNotificationConfig(getNotificationType)

        assertThat(result).isEqualTo(ResponseEntity<NotificationConfigResponse>(HttpStatus.NOT_FOUND))

        verify(exactly = 1) {
            notificationConfigServiceMockk.findByNotificationConfigType(any())
        }
    }

    @Test
    fun `should update notification config`() {
        val notificationConfigRequest = NotificationConfigRequest(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )
        every { notificationConfigServiceMockk.updateNotificationConfig(any()) } returns true

        val result = controller.updateNotificationConfig(notificationConfigRequest)

        assertThat(result).isEqualTo(
            ResponseEntity.ok().body(
                StatusResponse("Notification config updated successfully: test")
            )
        )

        verify(exactly = 1) {
            notificationConfigServiceMockk.updateNotificationConfig(any())
        }
    }

    @Test
    fun `should failed update notification config because notification config not found`() {
        val notificationConfigRequest = NotificationConfigRequest(
            notificationType = "test",
            rateLimitInterval = 1,
            rateLimitCount = 1
        )
        every { notificationConfigServiceMockk.updateNotificationConfig(any()) } returns false

        val result = controller.updateNotificationConfig(notificationConfigRequest)

        assertThat(result).isEqualTo(
            ResponseEntity.unprocessableEntity().body(
                StatusResponse("Notification config does not exists: test")
            )
        )

        verify(exactly = 1) {
            notificationConfigServiceMockk.updateNotificationConfig(any())
        }
    }

    @Test
    fun `should delete notification config`() {
        val deleteNotificationType = "test"
        every { notificationConfigServiceMockk.deleteNotificationConfig(any()) } returns true

        val result = controller.deleteNotificationConfig(deleteNotificationType)

        assertThat(result).isEqualTo(
            ResponseEntity.ok().body(StatusResponse("Notification config deleted successfully: test"))
        )

        verify(exactly = 1) {
            notificationConfigServiceMockk.deleteNotificationConfig(any())
        }
    }

    @Test
    fun `should failed delete notification config because notification config not found`() {
        val deleteNotificationType = "test"
        every { notificationConfigServiceMockk.deleteNotificationConfig(any()) } returns false

        val result = controller.deleteNotificationConfig(deleteNotificationType)

        assertThat(result).isEqualTo(
            ResponseEntity.unprocessableEntity().body(StatusResponse("Notification config does not exists: test"))
        )

        verify(exactly = 1) {
            notificationConfigServiceMockk.deleteNotificationConfig(any())
        }
    }
}