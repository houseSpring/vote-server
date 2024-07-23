package house.spring.vote.usecase

import house.spring.vote.post.application.port.`in`.PostQueryService
import house.spring.vote.report.application.port.`in`.ReportService
import org.springframework.stereotype.Service

@Service
class PostReportUseCase(
    private val postQueryService: PostQueryService,
    private val reportService: ReportService,
) {
    fun execute(
        reporter: String,
        reportedPost: String,
    ) {
        postQueryService.getPost(reportedPost)
        reportService.reportPost(reportedPost)
    }
}