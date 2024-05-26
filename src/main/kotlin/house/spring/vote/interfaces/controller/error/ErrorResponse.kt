package house.spring.vote.interfaces.controller.error

data class ErrorResponse(
    val status: Int,
    val message: Any
) {
}