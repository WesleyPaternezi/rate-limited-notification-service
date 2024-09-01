package com.modak.rate_limited_notification_service.application.web.handler

data class ExceptionResponse(
    val statusCode: Int,
    val message: String,
    val errorCode: String? = null
)