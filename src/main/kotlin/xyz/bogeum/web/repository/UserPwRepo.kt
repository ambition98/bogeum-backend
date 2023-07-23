package xyz.bogeum.web.repository

import org.springframework.data.jpa.repository.JpaRepository
import xyz.bogeum.web.entity.UserPwEntity

interface UserPwRepo : JpaRepository<UserPwEntity, String>