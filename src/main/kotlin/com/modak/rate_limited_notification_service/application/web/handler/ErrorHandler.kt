package com.modak.rate_limited_notification_service.application.web.handler

import com.modak.rate_limited_notification_service.domain.exception.UnprocessableNotification
import org.apache.coyote.BadRequestException
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException.UnprocessableEntity

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            message = ex.localizedMessage,
            errorCode = "INTERNAL_SERVER_ERROR",
        )
    }

    @ExceptionHandler(UnprocessableEntity::class)
    fun handleUnprocessableEntity(ex: Exception): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
            message = ex.localizedMessage,
            errorCode = "UNPROCESSABLE_ENTITY",
        )
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException::class)
    fun handleNotFoundException(ex: ChangeSetPersister.NotFoundException): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.NOT_FOUND,
            message = ex.localizedMessage,
            errorCode = "NOT_FOUND",
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.BAD_REQUEST,
            message = ex.localizedMessage,
            errorCode = "BAD_REQUEST",
        )
    }

    @ExceptionHandler(UnprocessableNotification::class)
    fun handleUnprocessableNotification(ex: UnprocessableNotification): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
            message = ex.localizedMessage,
            errorCode = "UNPROCESSABLE_ENTITY",
        )
    }

    private fun buildExceptionResponse(
        httpStatus: HttpStatus,
        message: String,
        errorCode: String? = null,
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            httpStatus.value(),
            message,
            errorCode
        )
        return ResponseEntity.status(httpStatus).body(exceptionResponse)
    }
}