package com.mif.carservice.model

import java.time.Instant

data class DefectServiceEntity(
        val id: Int,
        val name: String,
        val description: String,
        val priority: String,
        val status: String,
        val dateCreated: Instant,
        val dateUpdated: Instant?
)
