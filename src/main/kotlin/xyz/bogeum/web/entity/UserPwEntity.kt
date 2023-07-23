package xyz.bogeum.web.entity

import de.huxhorn.sulky.ulid.ULID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "USER_PW")
class UserPwEntity(
    @Id
    @Column(columnDefinition = "BINARY(16)")
    val id: String = ULID().nextULID(),

    @Column(length = 68, nullable = false)
    val digest: String
)