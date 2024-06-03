package house.spring.vote.common.controller.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [EnumValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidEnum(
    val message: String = "Invalid enum value",
    val enumClass: KClass<out Enum<*>>,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)