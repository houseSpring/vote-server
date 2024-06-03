package house.spring.vote.common.domain.exception

object ErrorCode {
    const val INVALID_POLL_SIZE = "투표 항목 갯수가 올바르지 않습니다."
    const val INVALID_PICKED_POLL_SIZE = "투표 항목 갯수가 올바르지 않습니다."

    const val UNAUTHORIZED = "인증되지 않은 사용자입니다."

    const val POST_NOT_FOUND = "게시글을 찾을 수 없습니다."
    const val POLL_NOT_FOUND = "투표 항목을 찾을 수 없습니다."
    const val USER_NOT_FOUND = "사용자를 찾을 수 없습니다."

    const val ALREADY_REGISTERED_DEVICE = "이미 등록된 디바이스입니다."
    const val ALREADY_PICKED_POST = "이미 투표한 게시글입니다."

    const val FAIL_ON_COPY_IMAGE = "이미지 복사에 실패했습니다."
}