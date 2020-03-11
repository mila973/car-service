package com.mif.carservice.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mif.carservice.service.error.VehicleErrors
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.server.ResponseStatusException

object HttpClientUtils {
    fun handleHttpErrorStatus(it: Throwable) {
        if (it is HttpClientErrorException) {
            when (it.statusCode) {
                HttpStatus.NOT_FOUND ->
                    throw HttpException(status = HttpStatus.NOT_FOUND, code = DefectClientErrors.DefectNotFound, message = "Could not find defect")
                HttpStatus.CONFLICT ->
                    throw HttpException(status = HttpStatus.CONFLICT, code = DefectClientErrors.DefectConflict, message = null)
                HttpStatus.BAD_REQUEST ->
                    throw HttpException(status = HttpStatus.BAD_REQUEST, code = DefectClientErrors.DefectBadRequest, message = "Bad request sent to defect service")
                else -> throw HttpException(status = it.statusCode, code = DefectClientErrors.DefectServiceUnavailable, message = "Something went wrong with defect service")
            }
        }
    }

}

enum class DefectClientErrors : ErrorCode {
    DefectNotFound,
    DefectConflict,
    DefectBadRequest,
    DefectServiceUnavailable
}


interface ErrorCode {
    val name: String
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