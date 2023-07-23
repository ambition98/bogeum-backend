package xyz.bogeum.web.service

import xyz.bogeum.web.entity.UserPwEntity
import xyz.bogeum.web.repository.UserPwRepo

class UserPwServ(
    private val userPwRepo: UserPwRepo
) {

    fun getDigetById(id: String) = userPwRepo.findById(id).get().digest
    fun save(entity: UserPwEntity) = userPwRepo.save(entity)
}