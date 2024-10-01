package house.spring.vote.post.infrastructure.repository

import house.spring.vote.post.application.port.out.mapper.PickedPollMapper
import house.spring.vote.post.application.port.out.repository.PickedPollRepository
import house.spring.vote.post.domain.model.PickedPoll
import house.spring.vote.post.infrastructure.entity.PickedPollEntity
import org.springframework.stereotype.Repository

@Repository
class PickedPollRepositoryImpl(
    private val pickedPollJpaRepository: PickedPollJpaRepository,
    private val pickedPollMapper: PickedPollMapper,
) : PickedPollRepository {
    override fun existsByPostIdAndUserId(postId: String, userId: String): Boolean {
        return pickedPollJpaRepository.existsByPostIdAndUserId(postId, userId)
    }

    override fun saveAll(pickedPolls: List<PickedPoll>): List<PickedPoll> {
        val pickedPollEntities = pickedPollMapper.toEntities(pickedPolls)
        return pickedPollMapper.toDomains(pickedPollJpaRepository.saveAll(pickedPollEntities))
    }

    override fun findAllByUserIdAndPostIds(userId: String, postIds: List<String>): List<PickedPollEntity> {
        return pickedPollJpaRepository.findAllByUserIdAndPostIds(userId, postIds)
    }
}