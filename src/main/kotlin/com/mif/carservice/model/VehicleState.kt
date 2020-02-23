package com.mif.carservice.model

enum class VehicleState {
    SCHEDULED,
    REPAIRING,
    REPAIRED,
    UNKNOWN;

    companion object {
        fun transitionState(state: VehicleState): VehicleState {
            return when(state) {
                SCHEDULED -> REPAIRING
                REPAIRING -> REPAIRED
                else -> UNKNOWN
            }
        }
    }
}