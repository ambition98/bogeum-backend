package xyz.bogeum.web.entity

import xyz.bogeum.enum.LoginPlatform
import xyz.bogeum.enum.UserRole
import java.util.UUID
import javax.persistence.*

@Entity(name = "ACCOUNT")
@Table
class AccountEntity (
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @Column(length = 50, nullable = false, unique = true)
    val email: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val loginPlatform: LoginPlatform,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole = UserRole.GUEST,

    @Column(nullable = false)
    var isVerified: Boolean = false
) : BaseEntity() {

    @Column(length = 50)
    var profileImage: String? = null

//    @Column(length = 250)
//    var accessToken: String? = null

    @Column(length = 250)
    var refreshToken: String? = null
}