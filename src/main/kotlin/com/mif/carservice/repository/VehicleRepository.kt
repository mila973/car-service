package com.mif.carservice.repository

import com.mif.carservice.model.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface VehicleRepository : JpaRepository<Vehicle, UUID> {
    fun findVehicleById(id: UUID): Vehicle?
}