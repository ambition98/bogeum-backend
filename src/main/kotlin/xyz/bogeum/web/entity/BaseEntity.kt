package xyz.bogeum.web.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass


@MappedSuperclass
open class BaseEntity {
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @CreationTimestamp
    open val createdAt: Date? = null

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    open val updatedAt: Date? = null
}