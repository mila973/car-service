package com.mif.carservice.controller.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "ScheduleVehicleRequest")
data class ScheduleRequest(

        @JsonProperty("registrationNumber")
        @ApiModelProperty(value = "Vehicle registration number", example = "AAA777")
        val registrationNumber: String
)