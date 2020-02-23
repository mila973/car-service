package com.mif.carservice.service

import com.mif.carservice.model.Vehicle
import com.mif.carservice.model.VehicleState
import com.mif.carservice.repository.VehicleRepository
import com.mif.carservice.service.error.EmailRequestErrors
import com.mif.carservice.service.error.HttpException
import com.mif.carservice.service.exception.VehicleNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CarManagementService(
        private var vehicleRepository: VehicleRepository
) {

    fun getScheduledVehicles(): List<Vehicle> {
        return vehicleRepository
                .findAll()
                .filter { it.state.equals(VehicleState.SCHEDULED) }
    }

    fun scheduleVehicle(registrationNumber: String): String {
        val vehicle = Vehicle(
                registrationNumber = registrationNumber,
                state = VehicleState.SCHEDULED
        )
        vehicleRepository.save(vehicle)
        return vehicle.id.toString()
    }

    fun initiateRepairing(vehicleId: UUID): String {
        var vehicle = vehicleRepository.findCarById(vehicleId) ?:
            throw createNotFoundException(vehicleId.toString())
        if (VehicleState.transitionState(vehicle.state) != VehicleState.REPAIRING) {
            buildIllegalStateException(vehicleId, vehicle.state)
        }
        vehicle.state = VehicleState.REPAIRING
        vehicleRepository.save(vehicle)

        return vehicle.id.toString()
    }

    fun finishRepairing(vehicleId: UUID): String {
        var vehicle = vehicleRepository.findCarById(vehicleId) ?:
                throw createNotFoundException(vehicleId.toString())
        if (VehicleState.transitionState(vehicle.state) != VehicleState.REPAIRED) {
            buildIllegalStateException(vehicleId, vehicle.state)
        }
        vehicle.state = VehicleState.REPAIRED
        return vehicle.id.toString()
    }

    fun deleteVehicle(vehicleId: UUID): String {
        var vehicle = vehicleRepository.findCarById(vehicleId) ?:
            throw createNotFoundException(vehicleId.toString())
        vehicleRepository.delete(vehicle)

        return vehicle.id.toString()
    }

    fun buildIllegalStateException(id: UUID, state: VehicleState) {
        throw HttpException(
                status = HttpStatus.NOT_ACCEPTABLE,
                code = EmailRequestErrors.IllegalVehicleState,
                message = "Vehicle cannot initiate action in state $state for order id: $id"
        )
    }

    fun createNotFoundException(vehicleId: String): HttpException {
        return HttpException(
                status = HttpStatus.NOT_FOUND,
                code = EmailRequestErrors.VehicleNotFound,
                message = "Failed to find vehicle with id: $vehicleId"
        )
    }
}