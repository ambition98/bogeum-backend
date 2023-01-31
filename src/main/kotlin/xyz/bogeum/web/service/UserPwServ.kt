package xyz.bogeum.web.service

import org.springframework.stereotype.Service
import xyz.bogeum.web.entity.UserPwEntity
import xyz.bogeum.web.repository.UserPwRepo
import java.util.*

@Service
class UserPwServ(
    private val userPwRepo: UserPwRepo
) {

    fun getDigetById(id: UUID) = userPwRepo.findById(id).get().digest
    fun save(entity: UserPwEntity) = userPwRepo.save(entity)
}