package com.mif.carservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "defect")
data class Defect(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: UUID = UUID.randomUUID(),

        @Column(name = "service_id")
        val serviceId: Int,

        @JsonIgnore
        @Column(name = "vehicle_id")
        val vehicleId: UUID
)