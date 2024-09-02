package com.modak.rate_limited_notification_service.resources.gateways

import com.modak.rate_limited_notification_service.resources.gateways.client.NotificationFeignClient
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class NotificationFeignGatewayTest {
    private val notificationFeignClientMock = mockk<NotificationFeignClient>()

    private val notificationFeignGateway =
        NotificationFeignGateway(notificationFeignClient = notificationFeignClientMock)


    @Test
    fun `should send notification`() {

        every { notificationFeignGateway.send(any(), any(), any()) } just runs

        notificationFeignGateway.send(
            userId = "user-test",
            message = "message-test",
            notificationType = "notification-type-test"
        )

        verify(exactly = 1) {
            notificationFeignClientMock.sendNotification(any(), any(), any())
        }
    }

    @Test
    fun `should throw exception`() {
        every { notificationFeignGateway.send(any(), any(), any()) } throws Exception()
        assertThrows<Exception> {
            notificationFeignGateway.send(
                userId = "user-test",
                message = "message-test",
                notificationType = "notification-type-test"
            )
        }
        verify(exactly = 1) {
            notificationFeignClientMock.sendNotification(any(), any(), any())
        }
    }
}