package com.mif.carservice.service.error

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus

interface ErrorCode {
    val name: String
}

enum class EmailRequestErrors: ErrorCode {
    IllegalVehicleState,
    VehicleNotFound
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class HttpException(
        val status: HttpStatus,
        val code: ErrorCode,
        message: String?,
        val data: Map<String, Any> = mapOf(),
        e: Throwable? = null
) : Exception(message, e)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class HttpErrorResponse(val code: String, val message: String?, val data: Map<String, Any>? = mapOf()) {
    companion object {
        fun fromException(e: Exception, data: Map<String, Any> = mapOf()) =
                HttpErrorResponse(e.javaClass.simpleName, e.message, data)
    }
}