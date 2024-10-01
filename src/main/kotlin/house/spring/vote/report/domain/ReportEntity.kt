package house.spring.vote.report.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes


@Entity()
@Table(name = "report")
class ReportEntity(
    @Id
    val userId: String,
) {
    @Column(nullable = false, columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    val userIds: MutableSet<String> = mutableSetOf()

    @Column(nullable = false, columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    val postIds: MutableSet<String> = mutableSetOf()
}