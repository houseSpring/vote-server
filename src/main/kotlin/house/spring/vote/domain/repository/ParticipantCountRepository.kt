package house.spring.vote.domain.repository

interface ParticipantCountRepository {
    fun getPostIdToCountMap(ids: List<Long>): Map<Long, Int>
    fun getPostCountById(id: Long): Int
    fun getPollIdToCountMap(ids: List<Long>): Map<Long, Int>
    fun countUpPost(postId: Long)
    fun countUpPolls(pollIds: List<Long>)
}