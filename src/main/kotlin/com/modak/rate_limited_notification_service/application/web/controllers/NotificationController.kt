package com.modak.rate_limited_notification_service.application.web.controllers

import com.modak.rate_limited_notification_service.application.web.requests.NotificationRequest
import com.modak.rate_limited_notification_service.application.web.requests.toModel
import com.modak.rate_limited_notification_service.application.web.responses.NotificationResponse
import com.modak.rate_limited_notification_service.domain.services.NotificationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/notification")
class NotificationController(
    val notificationService: NotificationService
) {

    @PostMapping("/notify")
    @ResponseStatus(HttpStatus.CREATED)
    fun notify(@RequestBody notificationRequest: NotificationRequest): ResponseEntity<NotificationResponse> {

        notificationService.sendAndPersistNotification(notificationRequest.toModel())

        val locationURI = URI.create("/notification/notify")
        return ResponseEntity.created(locationURI).body(
            NotificationResponse(
                userId = notificationRequest.userId,
                notificationType = notificationRequest.notificationType,
                contentBody = notificationRequest.contentBody
            )
        )
    }

    //TODO implement bulk
//    @PostMapping("/notify-bulk")
//    @ResponseStatus(HttpStatus.CREATED)
//    fun notifyBulk(@RequestBody notificationRequest: List<NotificationRequest>): ResponseEntity<ListNotificationResponse> {
//
//        val result = notificationService.saveAndSendAllNotifications(notificationRequest.map { it.toModel() })
//
//        val locationURI = URI.create("/notification/notify-bulk")
//
//        return ResponseEntity.created(locationURI).body(ListNotificationResponse(emptyList(), emptyList()))
//    }
}