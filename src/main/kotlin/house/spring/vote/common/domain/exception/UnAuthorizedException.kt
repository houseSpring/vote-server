package house.spring.vote.common.domain.exception

import org.springframework.http.HttpStatus

class UnAuthorizedException(message: String) : CustomException(HttpStatus.UNAUTHORIZED, message) {
}