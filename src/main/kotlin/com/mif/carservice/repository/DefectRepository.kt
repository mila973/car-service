package com.mif.carservice.repository

import com.mif.carservice.model.Defect
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DefectRepository : JpaRepository<Defect, UUID> {
    fun findDefectById(id: UUID): Defect?

    fun findDefectByServiceId(id: Int): Defect?
}