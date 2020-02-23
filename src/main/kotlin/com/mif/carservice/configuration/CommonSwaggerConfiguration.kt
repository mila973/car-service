package com.mif.carservice.configuration

import com.fasterxml.classmate.TypeResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.async.DeferredResult
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.AlternateTypeRules.newRule
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.lang.reflect.WildcardType
import java.util.concurrent.CompletableFuture

@Configuration
@EnableSwagger2
class CommonSwaggerConfiguration {
    @Autowired
    private val typeResolver: TypeResolver? = null

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mif.carservice"))
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity::class.java, CompletableFuture::class.java)
                .alternateTypeRules(
                        newRule(typeResolver!!.resolve(
                                DeferredResult::class.java,
                                typeResolver.resolve(ResponseEntity::class.java)
                        ),
                                typeResolver.resolve(WildcardType::class.java)
                        )
                )
                .useDefaultResponseMessages(false)
    }
}