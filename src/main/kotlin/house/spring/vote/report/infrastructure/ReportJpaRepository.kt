package house.spring.vote.report.infrastructure

import house.spring.vote.report.domain.ReportEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReportJpaRepository : JpaRepository<ReportEntity, String>