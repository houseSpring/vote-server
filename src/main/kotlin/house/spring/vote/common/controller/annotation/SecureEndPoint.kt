package house.spring.vote.common.controller.annotation

import io.swagger.v3.oas.annotations.security.SecurityRequirement

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@SecurityRequirement(name = "bearerAuth")
annotation class SecureEndPoint
