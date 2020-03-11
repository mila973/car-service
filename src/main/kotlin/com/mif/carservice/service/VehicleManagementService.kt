package com.mif.carservice.service

import com.mif.carservice.model.Vehicle
import com.mif.carservice.model.VehicleState
import com.mif.carservice.repository.VehicleRepository
import com.mif.carservice.service.error.VehicleErrors
import com.mif.carservice.util.HttpException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VehicleManagementService(
        private var vehicleRepository: VehicleRepository
) {

    fun getScheduledVehicles(): List<Vehicle> {
        return vehicleRepository
                .findAll()
    }

    fun getVehicle(vehicleId: UUID): Vehicle {
        return vehicleRepository
                .findVehicleById(vehicleId) ?:
                    throw createNotFoundException(vehicleId.toString())

    }

    fun scheduleVehicle(registrationNumber: String): String {
        val vehicle = Vehicle(
                registrationNumber = registrationNumber,
                state = VehicleState.SCHEDULED
        )
        return vehicleRepository.save(vehicle).id.toString()
    }

    fun updateVehicle(vehicleId: UUID, registrationNumber: String): Vehicle {
        var vehicle = vehicleRepository.findVehicleById(vehicleId) ?:
            throw createNotFoundException(vehicleId.toString())

        vehicle.registrationNumber = registrationNumber
        vehicleRepository.save(vehicle)

        return vehicle
    }

    fun initiateRepairing(vehicleId: UUID): String {
        var vehicle = vehicleRepository.findVehicleById(vehicleId) ?:
            throw createNotFoundException(vehicleId.toString())
        if (VehicleState.transitionState(vehicle.state) != VehicleState.REPAIRING) {
            buildIllegalStateException(vehicleId, vehicle.state)
        }
        vehicle.state = VehicleState.REPAIRING
        vehicleRepository.save(vehicle)

        return vehicle.id.toString()
    }

    fun finishRepairing(vehicleId: UUID): String {
        var vehicle = vehicleRepository.findVehicleById(vehicleId) ?:
                throw createNotFoundException(vehicleId.toString())
        if (VehicleState.transitionState(vehicle.state) != VehicleState.REPAIRED) {
            buildIllegalStateException(vehicleId, vehicle.state)
        }
        vehicle.state = VehicleState.REPAIRED
        return vehicle.id.toString()
    }

    fun deleteVehicle(vehicleId: UUID): String {
        var vehicle = vehicleRepository.findVehicleById(vehicleId) ?:
            throw createNotFoundException(vehicleId.toString())
        vehicleRepository.delete(vehicle)

        return vehicle.id.toString()
    }

    fun buildIllegalStateException(id: UUID, state: VehicleState) {
        throw HttpException(
                status = HttpStatus.NOT_ACCEPTABLE,
                code = VehicleErrors.IllegalVehicleState,
                message = "Vehicle cannot initiate action in state $state for order id: $id"
        )
    }

    fun createNotFoundException(vehicleId: String): HttpException {
        return HttpException(
                status = HttpStatus.NOT_FOUND,
                code = VehicleErrors.VehicleNotFound,
                message = "Failed to find vehicle with id: $vehicleId"
        )
    }
}