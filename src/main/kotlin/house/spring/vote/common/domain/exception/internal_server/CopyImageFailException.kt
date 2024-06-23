package house.spring.vote.common.domain.exception.internal_server

import house.spring.vote.common.domain.exception.ErrorCode

class CopyImageFailException : InternalServerException(ErrorCode.FAIL_ON_COPY_IMAGE) {
}