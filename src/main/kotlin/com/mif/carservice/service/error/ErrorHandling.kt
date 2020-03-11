package com.mif.carservice.service.error

import com.mif.carservice.util.ErrorCode


enum class VehicleErrors : ErrorCode {
    IllegalVehicleState,
    VehicleNotFound
}

enum class DefectErrors: ErrorCode {
    DefectNotFound
}