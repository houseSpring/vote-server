package house.spring.vote.util.excaption

import org.springframework.http.HttpStatus

class UnAuthorizedException(message: String) : CustomException(HttpStatus.UNAUTHORIZED, message) {
}