package house.spring.vote.report.application.port.`in`

import house.spring.vote.report.controller.response.GetReportsResponse

interface ReportService {
    fun reportUser(reporterId: String, userId: String)
    fun reportPost(reporterId: String, postId: String)
    fun getReports(userId: String): GetReportsResponse
}