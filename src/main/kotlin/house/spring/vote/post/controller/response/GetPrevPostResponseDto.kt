package house.spring.vote.post.controller.response

data class GetPrevPostResponseDto(
    val unVotedPostIds: List<Post>,
) {
    data class Post(
        val id: String,
        val isVoted: Boolean,
    )
}
