package xyz.bogeum.redis.entity

import org.springframework.data.redis.core.RedisHash
import java.util.*
import javax.persistence.Id

@RedisHash(value = "verify", timeToLive = 300)
class VerifyAccount(
    @Id
    private val id: UUID,
    private val code: String
)