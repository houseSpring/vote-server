package house.spring.vote.report.controller

import house.spring.vote.common.controller.annotation.CurrentUser
import house.spring.vote.common.controller.annotation.SecureEndPoint
import house.spring.vote.report.application.port.`in`.ReportService
import house.spring.vote.report.controller.response.GetReportsResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import house.spring.vote.common.domain.CurrentUser as LoginUser

@RestController
class ReportController(
    private val reportService: ReportService,
) {
    @SecureEndPoint
    @GetMapping("/reports")
    fun getReports(
        @CurrentUser user: LoginUser,
    ): ResponseEntity<GetReportsResponse> {
        return ResponseEntity.ok().body(reportService.getReports(user.id))
    }

    @SecureEndPoint
    @PostMapping("/reports/user/{userId}")
    fun reportUser(
        @CurrentUser user: LoginUser,
        @PathVariable userId: String,
    ): ResponseEntity<Unit> {
        reportService.reportUser(
            reporterId = user.id,
            userId = userId
        )
        return ResponseEntity.ok().build()
    }

    @SecureEndPoint
    @PostMapping("/reports/post/{postId}")
    fun reportPost(
        @CurrentUser user: LoginUser,
        @PathVariable postId: String,
    ): ResponseEntity<Unit> {
        reportService.reportPost(
            reporterId = user.id,
            postId = postId
        )
        return ResponseEntity.ok().build()
    }
}