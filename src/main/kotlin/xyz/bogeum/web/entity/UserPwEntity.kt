package xyz.bogeum.web.entity

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "USER_PW")
class UserPwEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(length = 68, nullable = false)
    val digest: String
)