package xyz.bogeum.web.repository

import org.springframework.data.jpa.repository.JpaRepository
import xyz.bogeum.web.entity.AccountEntity
import java.util.*

interface AccountRepo : JpaRepository<AccountEntity, UUID> {
    fun findByEmail(email: String): AccountEntity?
    fun save(entity: AccountEntity): AccountEntity
    fun existsByEmail(email: String): Boolean
}