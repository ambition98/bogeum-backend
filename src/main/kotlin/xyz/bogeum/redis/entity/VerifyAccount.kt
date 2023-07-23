package xyz.bogeum.redis.entity

import org.springframework.data.redis.core.RedisHash
import javax.persistence.Id

@RedisHash(value = "verify", timeToLive = 300)
class VerifyAccount(
    @Id
    val id: String,
    val code: String
)