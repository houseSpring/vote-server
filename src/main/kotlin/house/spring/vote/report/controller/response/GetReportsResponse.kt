package house.spring.vote.report.controller.response

data class GetReportsResponse(
    val reportedPostIds: List<String> = emptyList(),
    val reportedUserIds: List<String> = emptyList(),
)
