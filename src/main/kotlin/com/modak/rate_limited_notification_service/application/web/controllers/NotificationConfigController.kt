package com.modak.rate_limited_notification_service.application.web.controllers

import com.modak.rate_limited_notification_service.application.web.requests.NotificationConfigRequest
import com.modak.rate_limited_notification_service.application.web.requests.toModel
import com.modak.rate_limited_notification_service.application.web.responses.NotificationConfigResponse
import com.modak.rate_limited_notification_service.application.web.responses.StatusResponse
import com.modak.rate_limited_notification_service.application.web.responses.toResponse
import com.modak.rate_limited_notification_service.domain.services.NotificationConfigService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/notification-config")
class NotificationConfigController(
    val notificationConfigService: NotificationConfigService
) {

    @PostMapping
    fun createNotificationConfig(@Valid @RequestBody notificationConfigRequest: NotificationConfigRequest): ResponseEntity<StatusResponse> {
        val result = notificationConfigService.saveNewNotificationConfig(notificationConfigRequest.toModel())

        if (result) {
            val locationUri = URI.create("/notification-config")
            return ResponseEntity.created(locationUri)
                .body(StatusResponse("Notification config created successfully: ${notificationConfigRequest.notificationType}"))
        }
        return ResponseEntity.unprocessableEntity()
            .body(StatusResponse("Notification config already exists: ${notificationConfigRequest.notificationType}"))
    }


    @GetMapping("/{notificationType}")
    fun getNotificationConfig(@Valid @PathVariable notificationType: String): ResponseEntity<NotificationConfigResponse> {
        val result = notificationConfigService.findByNotificationConfigType(notificationType)
        return if (result != null) {
            ResponseEntity.ok().body(result.toResponse())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping
    fun updateNotificationConfig(@Valid @RequestBody notificationConfigRequest: NotificationConfigRequest): ResponseEntity<StatusResponse> {
        val result = notificationConfigService.updateNotificationConfig(notificationConfigRequest.toModel())
        return if (result) {
            ResponseEntity.ok()
                .body(StatusResponse("Notification config updated successfully: ${notificationConfigRequest.notificationType}"))
        } else {
            ResponseEntity.unprocessableEntity()
                .body(StatusResponse("Notification config does not exists: ${notificationConfigRequest.notificationType}"))
        }
    }

    @DeleteMapping("/{notificationType}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteNotificationConfig(@Valid @PathVariable notificationType: String): ResponseEntity<StatusResponse> {
        val result = notificationConfigService.deleteNotificationConfig(notificationType)
        return if (result) {
            ResponseEntity.ok()
                .body(StatusResponse("Notification config deleted successfully: $notificationType"))
        } else {
            ResponseEntity.unprocessableEntity()
                .body(StatusResponse("Notification config does not exists: $notificationType"))
        }
    }
}