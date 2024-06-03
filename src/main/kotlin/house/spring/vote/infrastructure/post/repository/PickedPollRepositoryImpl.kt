package house.spring.vote.infrastructure.post.repository

import house.spring.vote.application.post.port.PickedPollMapper
import house.spring.vote.domain.post.model.PickedPoll
import house.spring.vote.application.post.repository.PickedPollRepository
import org.springframework.stereotype.Repository

@Repository
class PickedPollRepositoryImpl(
    private val pickedPollJpaRepository: PickedPollJpaRepository,
    private val pickedPollMapper: PickedPollMapper
) : PickedPollRepository {
    override fun existsByPostIdAndUserId(postId: Long, userId: Long): Boolean {
        return pickedPollJpaRepository.existsByPostIdAndUserId(postId, userId)
    }

    override fun saveAll(pickedPolls: List<PickedPoll>): List<PickedPoll> {
        val pickedPollEntities = pickedPollMapper.toEntities(pickedPolls)
        return pickedPollMapper.toDomains(pickedPollJpaRepository.saveAll(pickedPollEntities))
    }
}