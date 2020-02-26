package com.mif.carservice.model

import java.time.Instant
import java.time.Instant.now
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "vehicle")
data class Vehicle(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: UUID = UUID.randomUUID(),

        @Column(name = "registration_number")
        var registrationNumber: String = "",

        @Column(name = "state")
        @Enumerated(EnumType.STRING)
        var state: VehicleState = VehicleState.SCHEDULED,

        @Column(name = "registration_date")
        val registeredOn: Instant = now()
)