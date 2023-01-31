package xyz.bogeum.web.entity

import javax.persistence.*

@Entity
class LoginPlatformEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(length = 20)
    val name: String
)
