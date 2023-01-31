package xyz.bogeum.web.model

import xyz.bogeum.enum.LoginPlatform
import xyz.bogeum.enum.UserRole
import xyz.bogeum.web.entity.AccountEntity
import java.util.*

class AccountDto(
    val id: UUID,
    val email: String,
    val isVerified: Boolean,
    val loginPlatform: LoginPlatform,
    val role: UserRole,
    val profileImage: String?
) {
    companion object {
        fun build(entity: AccountEntity): AccountDto {
            return AccountDto(
                entity.id,
                entity.email,
                entity.isVerified,
                entity.loginPlatform,
                entity.role,
                entity.profileImage
            )
        }
    }
}