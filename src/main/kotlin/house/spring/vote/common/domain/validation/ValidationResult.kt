package house.spring.vote.common.domain.validation

import house.spring.vote.common.domain.exception.CustomException

sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(val exception: CustomException) : ValidationResult()
}