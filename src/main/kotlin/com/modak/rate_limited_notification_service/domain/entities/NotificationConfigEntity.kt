package com.modak.rate_limited_notification_service.domain.entities

import com.modak.rate_limited_notification_service.domain.models.NotificationConfigModel
import jakarta.persistence.*

@Entity
@Table(name = "notification_config")
data class NotificationConfigEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "notification_type")
    val notificationType: String,

    @Column(name = "rate_limit_interval")
    var rateLimitInterval: Int,

    @Column(name = "rate_limit_count")
    var rateLimitCount: Int,
)

fun NotificationConfigEntity.toModel() = NotificationConfigModel(
    notificationType = notificationType,
    rateLimitInterval = rateLimitInterval,
    rateLimitCount = rateLimitCount
)
