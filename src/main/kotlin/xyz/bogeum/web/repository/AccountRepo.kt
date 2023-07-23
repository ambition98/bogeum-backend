package xyz.bogeum.web.repository

import org.springframework.data.jpa.repository.JpaRepository
import xyz.bogeum.web.entity.AccountEntity

interface AccountRepo : JpaRepository<AccountEntity, String> {
    fun findByEmail(email: String): AccountEntity?
    fun save(entity: AccountEntity): AccountEntity
    fun existsByEmail(email: String): Boolean
}