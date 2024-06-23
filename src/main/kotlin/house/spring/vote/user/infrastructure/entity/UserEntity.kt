package house.spring.vote.user.infrastructure.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity()
@Table(name = "app_user", indexes = [Index(name = "idx_device_id", columnList = "deviceId")])
class UserEntity(
    @Id
    val id: String,
    @Column(nullable = false)
    var nickname: String,
    @Column(nullable = true)
    var deviceId: String? = null,
) {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
}