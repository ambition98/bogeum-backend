package xyz.bogeum.web.repository

import org.springframework.data.jpa.repository.JpaRepository
import xyz.bogeum.web.entity.LoginPlatformEntity

interface LoginPlatformRepo : JpaRepository<LoginPlatformEntity, Long> {
    override fun findAll(): List<LoginPlatformEntity>
}