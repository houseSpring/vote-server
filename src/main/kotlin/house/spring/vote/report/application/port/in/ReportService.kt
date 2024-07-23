package house.spring.vote.report.application.port.`in`

interface ReportService {
    fun reportUser(userId: String)
    fun reportPost(postId: String)
}