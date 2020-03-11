package com.mif.carservice.component

import com.mif.carservice.model.DefectServiceEntity
import com.mif.carservice.model.DefectServiceRequest
import com.mif.carservice.util.HttpClientUtils.handleHttpErrorStatus
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

class DefectClient(
        val restTemplate: RestTemplate
) {
    companion object {
        const val DEFECTS_URI = "/defects"
    }

    fun getDefectList(): List<DefectServiceEntity> {
        val request = buildRequest(DEFECTS_URI, "{}", HttpMethod.GET)
        return restTemplate.runCatching {
            exchange<List<DefectServiceEntity>>(request).body!!
        }
                .onFailure(::handleFailure)
                .getOrThrow()
    }

    fun getDefect(id: Int): DefectServiceEntity {
        val request = buildRequest(DEFECTS_URI + "/$id", "{}", HttpMethod.GET)
        return restTemplate.runCatching {
            exchange<DefectServiceEntity>(request).body!!
        }
                .onFailure(::handleFailure)
                .getOrThrow()
    }

    fun updateDefect(id: Int, request: DefectServiceRequest): DefectServiceEntity {
        val request = buildRequest(DEFECTS_URI + "/$id", request, HttpMethod.PUT)

        return restTemplate.runCatching {
            exchange<DefectServiceEntity>(request).body!!
        }
                .onFailure(::handleFailure)
                .getOrThrow()
    }

    fun createDefect(request: DefectServiceRequest): DefectServiceEntity {
        val request = buildRequest(DEFECTS_URI, request, HttpMethod.POST)

        return restTemplate.runCatching {
            exchange<DefectServiceEntity>(request).body!!
        }
                .onFailure(::handleFailure)
                .getOrThrow()
    }

    fun deleteDefect(id: Int) {
        val request = buildRequest(DEFECTS_URI + "/$id", "{}", HttpMethod.DELETE)

        restTemplate.runCatching {
            exchange<DefectServiceEntity>(request)
        }
                .onFailure(::handleFailure)
                .getOrThrow()
    }

    private fun handleFailure(throwable: Throwable) {
        handleHttpErrorStatus(throwable)
    }

    fun buildRequest(uri: String, body: Any, method: HttpMethod) = RequestEntity
            .method(method, restTemplate.uriTemplateHandler.expand(uri))
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
}