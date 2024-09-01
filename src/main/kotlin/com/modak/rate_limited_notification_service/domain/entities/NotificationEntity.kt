package com.modak.rate_limited_notification_service.domain.entities

import com.modak.rate_limited_notification_service.domain.models.NotificationModel
import jakarta.annotation.Nullable
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notification")
data class NotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: String,

    @Column(name = "notification_type")
    val notificationType: String,

    @Column(name = "content_body")
    @Nullable
    val contentBody: String?,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)

fun NotificationEntity.toModel() = NotificationModel(
    userId = userId,
    notificationType = notificationType,
    contentBody = contentBody,
    createdAt = createdAt
)
