package house.spring.vote.common.controller

import house.spring.vote.common.domain.CurrentUser
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import house.spring.vote.common.controller.annotation.CurrentUser as CurrentUserAnnotation

@Component
class CurrentUserArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(CurrentUserAnnotation::class.java) != null &&
                parameter.parameterType == CurrentUser::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val request: HttpServletRequest? = webRequest.getNativeRequest(HttpServletRequest::class.java)
        return request?.getAttribute("currentUser") as CurrentUser
    }
}