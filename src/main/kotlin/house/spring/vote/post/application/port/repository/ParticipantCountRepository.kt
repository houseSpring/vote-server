package house.spring.vote.post.application.port.repository

interface ParticipantCountRepository {
    fun getPostIdToCountMap(postIds: List<String>): Map<String, Int>
    fun getPostCountById(postId: String): Int
    fun getPollIdToCountMap(ids: List<String>): Map<String, Int>
    fun countUpPost(postId: String)
    fun countUpPolls(pollIds: List<String>)
}