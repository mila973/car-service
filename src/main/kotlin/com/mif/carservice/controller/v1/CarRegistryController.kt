package com.mif.carservice.controller.v1

import com.mif.carservice.controller.v1.model.ScheduleRequest
import com.mif.carservice.controller.v1.model.UpdateVehicleRequest
import com.mif.carservice.model.Vehicle
import com.mif.carservice.service.VehicleManagementService
import com.mif.carservice.util.HttpException
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
class VehicleRegistryController(
        private val vehicleManagementService: VehicleManagementService
) {

    @GetMapping("vehicles")
    @ApiResponses(
            ApiResponse(code = 200, message = "successfully returned vehicles"),
            ApiResponse(code = 404, message = "NOT FOUND")
    )
    fun getScheduledVehicles(): ResponseEntity<List<Vehicle>> {
        return ResponseEntity.ok(
                vehicleManagementService.getScheduledVehicles()
        )
    }

    @GetMapping("vehicles/{vehicleId}")
    @ApiResponses(
            ApiResponse(code = 200, message = "successfully scheduled vehicle"),
            ApiResponse(code = 400, message = "Bad request")
    )
    fun getVehicle(
            @PathVariable(name = "vehicleId") vehicleId: String
    ): ResponseEntity<Vehicle> {
        try {
            return ResponseEntity.ok(
                    vehicleManagementService.getVehicle(UUID.fromString(vehicleId))
            )
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @PostMapping("vehicles")
    @ApiResponses(
            ApiResponse(code = 200, message = "successfully scheduled vehicle"),
            ApiResponse(code = 404, message = "NOT FOUND"),
            ApiResponse(code = 500, message = "Failed to schedule vehicle")
    )
    fun scheduleCar(
            @RequestBody request: ScheduleRequest
    ): ResponseEntity<String> {
        try {
            val scheduledVehicleId = vehicleManagementService.scheduleVehicle(request.registrationNumber)
            return ResponseEntity.ok(scheduledVehicleId)
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @PutMapping("vehicles/{vehicleId}")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully initiated vehicle repairing"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "Vehicle not found"),
            ApiResponse(code = 406, message = "Illegal vehicle state")
    )
    fun updateVehicle(
            @PathVariable(name = "vehicleId") vehicleId: String,
            @RequestBody request: UpdateVehicleRequest
    ): ResponseEntity<Vehicle> {
        try {
            val updatedVehicleId = vehicleManagementService.updateVehicle(
                    UUID.fromString(vehicleId),
                    request.registrationNumber
            )
            return ResponseEntity.ok(updatedVehicleId)
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @PostMapping("vehicles/{vehicleId}:repair")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully repaired vehicle"),
            ApiResponse(code = 404, message = "Vehicle not found"),
            ApiResponse(code = 406, message = "Illegal vehicle state")
    )
    fun initiateRepairing(
            @PathVariable(name = "vehicleId") vehicleId: String
    ): ResponseEntity<String> {
        try {
            val updatedVehicleId = vehicleManagementService.initiateRepairing(UUID.fromString(vehicleId))
            return ResponseEntity.ok(updatedVehicleId)
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @PostMapping("vehicles/{vehicleId}:finish")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully repaired vehicle"),
            ApiResponse(code = 404, message = "Vehicle not found"),
            ApiResponse(code = 406, message = "Illegal vehicle state")
    )
    fun finishRepairing(
            @PathVariable(name = "vehicleId") vehicleId: String
    ): ResponseEntity<String> {
        try {
            val updatedVehicleId = vehicleManagementService.finishRepairing(UUID.fromString(vehicleId))
            return ResponseEntity.ok(updatedVehicleId)
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @DeleteMapping("vehicles/{vehicleId}")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully deleted vehicle"),
            ApiResponse(code = 404, message = "Vehicle not found"),
            ApiResponse(code = 500, message = "Failed to delete vehicle")
    )
    fun releaseVehicle(
            @PathVariable(name = "vehicleId") vehicleId: String
    ): ResponseEntity<String> {
        try {
            val deletedVehicleId = vehicleManagementService.deleteVehicle(UUID.fromString(vehicleId))
            return ResponseEntity.ok(deletedVehicleId)
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }
}