package house.spring.vote.common.config

import house.spring.vote.common.application.TokenProvider
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig {

    @Bean
    fun jwtFilter(tokenProvider: TokenProvider): FilterRegistrationBean<JwtFilter> {
        val registrationBean = FilterRegistrationBean<JwtFilter>()
        registrationBean.filter = JwtFilter(tokenProvider)

        registrationBean.addUrlPatterns("/posts/*")
        registrationBean.addUrlPatterns("/upload-url")
        registrationBean.addUrlPatterns("/users") // GET만

        return registrationBean
    }
}