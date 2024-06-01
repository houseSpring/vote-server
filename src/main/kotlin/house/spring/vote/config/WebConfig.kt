package house.spring.vote.config

import house.spring.vote.infrastructure.common.LoggingInterceptor
import org.springframework.context.annotation.Configuration
//import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(private val loggingInterceptor: LoggingInterceptor) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loggingInterceptor)
    }
// TODO: 현재 POST요청에 대해 403 존재
//    override fun addCorsMappings(registry: CorsRegistry) {
//        registry.addMapping("/**")
//            .allowedOriginPatterns("*")
//            .allowedMethods("GET", "POST", "PUT", "DELETE")
//            .allowedHeaders("*")
//            .allowCredentials(true)
//    }
}