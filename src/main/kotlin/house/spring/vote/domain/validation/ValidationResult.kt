package house.spring.vote.domain.validation

import house.spring.vote.util.excaption.CustomException

sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(val exception: CustomException) : ValidationResult()
}