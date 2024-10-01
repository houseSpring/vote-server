package house.spring.vote.report.application

import house.spring.vote.report.application.port.`in`.ReportService
import house.spring.vote.report.controller.response.GetReportsResponse
import house.spring.vote.report.infrastructure.ReportJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class ReportServiceImpl(
    private val reportJpaRepository: ReportJpaRepository,
) : ReportService {

    override fun getReports(userId: String): GetReportsResponse {
        val report = reportJpaRepository.findById(userId).getOrNull()
            ?: return GetReportsResponse()
        return GetReportsResponse(
            reportedPostIds = report.postIds.toList(),
            reportedUserIds = report.userIds.toList()
        )
    }

    @Transactional
    override fun reportPost(reporterId: String, postId: String) {
        val report = reportJpaRepository.findById(reporterId).get()
        report.postIds.add(postId)
        reportJpaRepository.save(report)
    }

    @Transactional
    override fun reportUser(reporterId: String, userId: String) {
        val report = reportJpaRepository.findById(reporterId).get()
        report.userIds.add(userId)
        reportJpaRepository.save(report)
    }
}