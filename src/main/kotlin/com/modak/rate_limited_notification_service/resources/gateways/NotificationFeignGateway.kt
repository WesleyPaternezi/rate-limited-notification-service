package com.modak.rate_limited_notification_service.resources.gateways

import com.modak.rate_limited_notification_service.domain.gateway.NotificationGateway
import com.modak.rate_limited_notification_service.resources.gateways.client.NotificationFeignClient
import org.springframework.stereotype.Component

@Component
class NotificationFeignGateway(
    private val notificationFeignClient: NotificationFeignClient
): NotificationGateway {
    override fun send(userId: String, message: String, notificationType: String) {
        notificationFeignClient.sendNotification(
            type = notificationType,
            userId = userId,
            message = message
        )
    }
}