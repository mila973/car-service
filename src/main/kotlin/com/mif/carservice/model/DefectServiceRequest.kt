package com.mif.carservice.model

import org.springframework.validation.annotation.Validated

@Validated
data class DefectServiceRequest(
        val name: String,
        val description: String,
        val priority: String,
        val status: String
)