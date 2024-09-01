package com.modak.rate_limited_notification_service.application.web.controllers

import com.modak.rate_limited_notification_service.application.web.requests.NotificationRequest
import com.modak.rate_limited_notification_service.application.web.responses.NotificationResponse
import com.modak.rate_limited_notification_service.domain.services.NotificationService
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import java.net.URI


class NotificationControllerTest {
    private val notificationServiceMockk: NotificationService = mockk<NotificationService>()

    private val controller: NotificationController = NotificationController(notificationServiceMockk)

    @Test
    fun `should send a notification to a user`() {
        val notificationRequest = NotificationRequest(
            userId = "test",
            notificationType = "test",
            contentBody = "test"
        )

        every { notificationServiceMockk.sendAndPersistNotification(any()) } just runs

        val result = controller.notify(notificationRequest)

        assertThat(result).isEqualTo(
            ResponseEntity.created(URI("/notification/notify")).body(
                NotificationResponse(
                    userId = notificationRequest.userId,
                    notificationType = notificationRequest.notificationType,
                    contentBody = notificationRequest.contentBody
                )
            )
        )
        verify(exactly = 1) {
            notificationServiceMockk.sendAndPersistNotification(any())
        }
    }
}