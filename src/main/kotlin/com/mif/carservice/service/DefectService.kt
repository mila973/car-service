package com.mif.carservice.service

import com.mif.carservice.component.DefectClient
import com.mif.carservice.model.Defect
import com.mif.carservice.model.DefectServiceEntity
import com.mif.carservice.model.DefectServiceRequest
import com.mif.carservice.repository.DefectRepository
import com.mif.carservice.service.error.DefectErrors
import com.mif.carservice.util.HttpException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DefectService(
        private val vehicleService: VehicleManagementService,
        private val defectRepository: DefectRepository,
        private val defectClient: DefectClient
) {

    fun getDefectList(): List<DefectServiceEntity> {
        return defectClient.getDefectList()
    }

    fun getDefect(id: Int): DefectServiceEntity {
        return defectClient.getDefect(id)
    }

    fun getVehicleDefects(vehicleId: UUID): List<DefectServiceEntity> {
        val vehicle = vehicleService.getVehicle(vehicleId)

        return defectClient.getDefectList()
                .filter {
                    vehicle.defects.any { vehicleDefect -> vehicleDefect.serviceId == it.id }
                }
    }

    fun updateDefect(id: Int, request: DefectServiceRequest): DefectServiceEntity {
        return defectClient.updateDefect(id, request)
    }

    fun createVehicleDefect(id: UUID, request: DefectServiceRequest): DefectServiceEntity {
        val serviceDefect = createDefect(request)
        val defect = Defect(
                serviceId = serviceDefect.id,
                vehicleId = id
        )
        defectRepository.save(defect)
        return serviceDefect
    }

    fun createDefect(request: DefectServiceRequest): DefectServiceEntity {
        return defectClient.createDefect(request)
    }

    fun deleteDefect(id: Int) {
        val defect = defectRepository.findDefectByServiceId(id) ?: throw createNotFoundException(id)
        defectClient.deleteDefect(defect.serviceId)
        defectRepository.delete(defect)
    }


    fun createNotFoundException(id: Any): HttpException {
        return HttpException(
                status = HttpStatus.NOT_FOUND,
                code = DefectErrors.DefectNotFound,
                message = "Failed to find defect with id: $id"
        )
    }
}