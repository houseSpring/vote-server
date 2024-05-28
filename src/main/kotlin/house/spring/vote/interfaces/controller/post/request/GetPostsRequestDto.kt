package house.spring.vote.interfaces.controller.post.request

import house.spring.vote.domain.post.model.SortBy
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern
import javax.swing.SortOrder

data class GetPostsRequestDto(
    @field:Pattern(
        regexp = "^[0-9]*\$",
        message = "cursor는 현재 숫자만 가능합니다."
    )
    @Schema(description = "서버에서 반환받은 cursor, 최초 요청일 경우 null", required = false)
    val cursor: String?,

//    @field:ValidEnum(enumClass = SortBy::class, message = "정렬 기준이 올바르지 않습니다.")
    @Schema(
        description = "정렬 기준",
        required = false,
        defaultValue = "CREATED_AT",
    )
    val sortBy: SortBy = SortBy.CREATED_AT,
    @Schema(
        description = "정렬 순서",
        required = false,
        defaultValue = "DESCENDING",
    ) val sortOrder: SortOrder = SortOrder.DESCENDING
)
