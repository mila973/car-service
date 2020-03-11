package com.mif.carservice.controller.v1

import com.mif.carservice.model.DefectServiceEntity
import com.mif.carservice.model.DefectServiceRequest
import com.mif.carservice.service.DefectService
import com.mif.carservice.util.HttpException
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Controller
@RequestMapping("v1")
class DefectController(
        val defectService: DefectService
) {

    @GetMapping("defects")
    @ApiResponses(
            ApiResponse(code = 200, message = "successfully returned defects"),
            ApiResponse(code = 404, message = "NOT FOUND")
    )
    fun getDefects(): ResponseEntity<List<DefectServiceEntity>> {
        try {
            return ResponseEntity.ok(
                    defectService.getDefectList()
            )
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @GetMapping("defects/{defectId}")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully returned defect"),
            ApiResponse(code = 404, message = "NOT FOUND")
    )
    fun getDefect(
            @PathVariable(name = "id") id: String
    ): ResponseEntity<DefectServiceEntity> {
        try {
            return ResponseEntity.ok(
                    defectService.getDefect(id.toInt())
            )
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @GetMapping("vehicles/{vehicleId}/defects")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully returned defects"),
            ApiResponse(code = 404, message = "NOT FOUND")
    )
    fun getVehicleDefects(
            @PathVariable(name = "vehicleId") id: String
    ): ResponseEntity<List<DefectServiceEntity>> {
        try {
            return ResponseEntity.ok(
                    defectService.getVehicleDefects(UUID.fromString(id))
            )
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @PostMapping("vehicles/{vehicleId}/defects")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully created defect"),
            ApiResponse(code = 404, message = "NOT FOUND"),
            ApiResponse(code = 500, message = "Failed to create defect")
    )
    fun createDefect(
            @PathVariable(name = "vehicleId") id: String,
            @RequestBody request: DefectServiceRequest
    ): ResponseEntity<DefectServiceEntity> {
        try {
            return ResponseEntity.ok(
                    defectService.createVehicleDefect(UUID.fromString(id), request)
            )
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @PutMapping("defects/{id}")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully updated defect"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "Defect not found"),
            ApiResponse(code = 406, message = "Illegal defect state")
    )
    fun updateDefect(
            @PathVariable(name = "id") id: String,
            @RequestBody request: DefectServiceRequest
    ): ResponseEntity<DefectServiceEntity> {
        try {
            return ResponseEntity.ok(
                    defectService.updateDefect(id.toInt(), request)
            )
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }

    @DeleteMapping("defects/{id}")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully deleted defect"),
            ApiResponse(code = 404, message = "Defect not found"),
            ApiResponse(code = 500, message = "Failed to delete defect")
    )
    fun deleteMapping(
            @PathVariable(name = "id") id: String
    ): ResponseEntity<Any> {
        try {
            defectService.deleteDefect(id.toInt())
            return ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: HttpException) {
            throw ResponseStatusException(ex.status, ex.message, ex)
        }
    }
}