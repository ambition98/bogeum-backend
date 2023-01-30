//package xyz.bogeum.web.service
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User
//import org.springframework.security.oauth2.core.user.OAuth2User
//import org.springframework.stereotype.Service
//import xyz.bogeum.auth.OauthAttributes
//import xyz.bogeum.auth.SessionUser
//import xyz.bogeum.exception.DataNotFoundException
//import xyz.bogeum.web.repository.UserRepo
//import java.util.Collections
//import javax.servlet.http.HttpSession
//
//@Service
//class OAuth2UserServ(
//    val userRepo: UserRepo,
//    val httpSession: HttpSession
//
//) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
//        val delegate = DefaultOAuth2UserService()
//        val oAuth2User = delegate.loadUser(userRequest)
//
//        val registrationId = userRequest?.clientRegistration?.registrationId
//            ?: throw OAuth2AuthenticationException("OAuth2 유저 로드 실패")
//        val userNameAttrName = userRequest.clientRegistration?.providerDetails?.userInfoEndpoint?.userNameAttributeName
//            ?: throw OAuth2AuthenticationException("OAuth2 유저 로드 실패")
//
//        val attributes = OauthAttributes.of(registrationId, userNameAttrName, oAuth2User.attributes)
//        val user = saveOrUpdate(attributes)
//        httpSession.setAttribute("user", SessionUser(user))
//
//        return DefaultOAuth2User(
//            Collections.singleton(SimpleGrantedAuthority(user.role.key)),
//            attributes.attributes,
//            attributes.nameAttributeKey
//        )
//    }
//
//    private fun saveOrUpdate(attributes: OauthAttributes)
//    = userRepo.save(
//        userRepo.findByEmail(attributes.email).also {
//            it?.name = attributes.name
//            it?.picture = attributes.picture
//        } ?: throw DataNotFoundException("\"${attributes.email}\" <- 존재하지 않는 이메일")
//    )
//}