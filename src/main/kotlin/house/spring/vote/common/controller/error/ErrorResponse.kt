package house.spring.vote.common.controller.error

data class ErrorResponse(
    val status: Int,
    val message: Any,
) {}