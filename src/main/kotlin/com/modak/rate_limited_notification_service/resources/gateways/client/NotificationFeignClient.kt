package com.modak.rate_limited_notification_service.resources.gateways.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "notification-client", url = "\${config.notification-url}")
interface NotificationFeignClient {

    @PostMapping(
        value = ["/send"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun sendNotification(
        @RequestParam type: String,
        @RequestParam userId: String,
        @RequestParam message: String
    )
}