//package xyz.bogeum.auth
//
//import xyz.bogeum.enum.LoginPlatform
//import xyz.bogeum.web.entity.UserEntity
//
//class OauthAttributes(
//    val attributes: Map<String, Any>,
//    val nameAttributeKey: String,
//    val name: String,
//    val email: String,
//    val picture: String,
//    private val loginPlatform: LoginPlatform
//) {
//    companion object {
//        fun of(registrationId: String, userNameAttributeName: String, attributes: Map<String, Any>)
//        = ofGoogle(userNameAttributeName, attributes) //임시
//
//        private fun ofGoogle(userNameAttributeName: String, attributes: Map<String, Any>)
//        = OauthAttributes(
//            attributes,
//            userNameAttributeName,
//            attributes["name"] as String,
//            attributes["email"] as String,
//            attributes["picture"] as String,
//            LoginPlatform.GOOGLE
//        )
//    }
//
//    fun toEntity() : UserEntity {
//        return UserEntity(
//            email = email ?: "",
//            picture = picture,
//            name = name,
//            loginPlatform = loginPlatform,
//        )
//    }
//}