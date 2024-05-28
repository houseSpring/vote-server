package house.spring.vote.util.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

// TODO: 의도와 달리 아예 동작하지 않음.
class EnumValidator : ConstraintValidator<ValidEnum, String> {
    private lateinit var enumValues: Array<String>

    override fun initialize(constraintAnnotation: ValidEnum) {
        enumValues = constraintAnnotation.enumClass.java.enumConstants.map { it.name }.toTypedArray()
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return value == null || enumValues.contains(value)
    }
}