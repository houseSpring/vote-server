package house.spring.vote.post.application.port.repository

interface ParticipantCountRepository {
    fun getPostsCount(postIds: List<String>): Map<String, Int>
    fun getPostCount(postId: String): Map<String, Int>
    fun countUpPickedPoll(postId: String, pollIds: List<String>)
}