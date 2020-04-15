package com.mif.carservice.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.mif.carservice.component.DefectClient
import com.sun.istack.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import java.time.Duration


@Component
@ConfigurationProperties("defect")
@Validated
class DefectTemplateProperties {
    @field:NotNull
    lateinit var url: String

    var connectTimeoutMs: Long = 2000

    var readTimeoutMs: Long = 5000

}

@Configuration
class DefectTemplateConfig {

    @Bean
    fun defectClient(
            builder: RestTemplateBuilder,
            props: DefectTemplateProperties,
            objectMapper: ObjectMapper
    ): DefectClient {
        val template = builder.rootUri(props.url)
                .interceptors(ClientHttpRequestInterceptor { request, body, execution ->
                    execution.execute(request, body)
                })
                .setConnectTimeout(Duration.ofMillis(props.connectTimeoutMs))
                .setReadTimeout(Duration.ofMillis(props.readTimeoutMs))
                .build()

        return DefectClient(template)
    }

}
