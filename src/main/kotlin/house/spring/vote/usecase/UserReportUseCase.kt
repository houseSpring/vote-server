package house.spring.vote.usecase

import house.spring.vote.report.application.port.`in`.ReportService
import house.spring.vote.user.application.port.`in`.UserQueryService
import org.springframework.stereotype.Service

@Service
class UserReportUseCase(
    private val userQueryService: UserQueryService,
    private val reportService: ReportService,
) {
    fun execute(
        reporter: String,
        reportedUser: String,
    ) {
        userQueryService.getUserInfoById(reportedUser)
        reportService.reportUser(reportedUser)
    }
}