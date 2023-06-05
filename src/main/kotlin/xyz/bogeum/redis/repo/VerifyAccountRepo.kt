package xyz.bogeum.redis.repo

import org.springframework.data.repository.CrudRepository
import xyz.bogeum.redis.entity.VerifyAccount
import java.util.*

interface VerifyAccountRepo : CrudRepository<VerifyAccount, String> {
    fun findById(id: UUID): VerifyAccount?
}