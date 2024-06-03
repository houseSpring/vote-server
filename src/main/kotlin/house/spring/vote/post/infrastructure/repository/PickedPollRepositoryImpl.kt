package house.spring.vote.post.infrastructure.repository

import house.spring.vote.post.application.port.mapper.PickedPollMapper
import house.spring.vote.post.domain.model.PickedPoll
import house.spring.vote.post.application.port.repository.PickedPollRepository
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