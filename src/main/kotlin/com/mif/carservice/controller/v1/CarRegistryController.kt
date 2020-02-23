package com.mif.carservice.controller.v1

import com.mif.carservice.controller.v1.model.ScheduleRequest
import com.mif.carservice.model.Vehicle
import com.mif.carservice.service.CarManagementService
import com.mif.carservice.service.error.HttpException
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID


@RestController
@RequestMapping("v1")
class CarRegistryController(
        private val carManagementService: CarManagementService
) {

    @GetMapping("cars")
    fun getScheduledVehicles(): ResponseEntity<List<Vehicle>> {
        return ResponseEntity.ok(
                carManagementService.getScheduledVehicles()
        )
    }

    @PostMapping("cars:schedule")
    @ApiResponses(
            ApiResponse(code = 200, message = "successfully scheduled vehicle"),
            ApiResponse(code = 404, message = "NOT FOUND"),
            ApiResponse(code = 500, message = "Failed to schedule vehicle")
    )
    fun scheduleCar(
            @RequestBody request: ScheduleRequest
    ): ResponseEntity<String> {
        try {
            val scheduledVehicleId = carManagementService.scheduleVehicle(request.registrationNumber)
            return ResponseEntity.ok(scheduledVehicleId)
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @PutMapping("cars/{vehicleId}:repair")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully initiated vehicle repairing"),
            ApiResponse(code = 404, message = "Vehicle not found"),
            ApiResponse(code = 406, message = "Illegal vehicle state")
    )
    fun repairCar(
            @PathVariable(name = "vehicleId") vehicleId: String
    ): ResponseEntity<String> {
        try {
            val updatedVehicleId = carManagementService.initiateRepairing(UUID.fromString(vehicleId))
            return ResponseEntity.ok(updatedVehicleId)
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @PutMapping("cars/{vehicleId}:finish")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully repaired vehicle"),
            ApiResponse(code = 404, message = "Vehicle not found"),
            ApiResponse(code = 406, message = "Illegal vehicle state")
    )
    fun finishRepairing(
            @PathVariable(name = "vehicleId") vehicleId: String
    ): ResponseEntity<String> {
        try {
            val updatedVehicleId = carManagementService.finishRepairing(UUID.fromString(vehicleId))
            return ResponseEntity.ok(updatedVehicleId)
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @DeleteMapping("cars/{vehicle}:release")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully deleted vehicle"),
            ApiResponse(code = 404, message = "Vehicle not found"),
            ApiResponse(code = 500, message = "Failed to delete vehicle")
    )
    fun releaseVehicle(
            @PathVariable(name = "vehicleId") vehicleId: String
    ): ResponseEntity<String> {
        try {
            val deletedVehicleId = carManagementService.deleteVehicle(UUID.fromString(vehicleId))
            return ResponseEntity.ok(deletedVehicleId)
        } catch(ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }
}