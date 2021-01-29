package com.donghyukki.korello.infrastructure.security.service

import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.domain.member.model.Role
import com.donghyukki.korello.domain.member.service.MemberCrudService
import com.donghyukki.korello.infrastructure.security.config.JwtConfig
import com.donghyukki.korello.infrastructure.security.model.OAuthAttributes
import com.donghyukki.korello.presentation.dto.MemberDTO
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import java.util.*

@Service
class CustomOAuth2UserService(
    private val memberCrudService: MemberCrudService,
    private val jwtConfig: JwtConfig
): OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {

        val registrationId = userRequest?.clientRegistration?.registrationId ?: throw IllegalStateException()
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)
        val nameAttributeKey = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        val oAuthAttributes = createOAuthAttributes(oAuth2User.name, registrationId, nameAttributeKey, oAuth2User.attributes)
        val (member, newAttributes) = authenticateMember(oAuthAttributes)

        return DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority(member.role))
            , newAttributes
            , oAuthAttributes.nameAttributeKey)

    }

    fun authenticateMember(oAuthAttributes: OAuthAttributes): Pair<Member, MutableMap<String, Any>> {
        val memberName = parseMemberName(oAuthAttributes.attributes)
        val providerId = parseProviderId(oAuthAttributes.attributes, oAuthAttributes.nameAttributeKey)
        val registrationId = oAuthAttributes.registrationId
        val role = Role.USER
        val accessToken = jwtConfig.createAccessToken(providerId, memberName)
        val refreshToken = jwtConfig.createRefreshToken(providerId, memberName)
        val newAttributes = oAuthAttributes.attributes.toMutableMap()

        newAttributes["accessToken"] = accessToken
        newAttributes["refreshToken"] = refreshToken

        val findMember = memberCrudService.findMemberByNameAndProviderId(memberName, providerId)

        if (!findMember.isPresent) {
            return Pair(memberCrudService.createMember(
                MemberDTO.Companion.Create(memberName
                    , role
                    , providerId
                    , registrationId
                    , accessToken
                    , refreshToken))
                , newAttributes)
        }

        return Pair(memberCrudService.changeAuth(MemberDTO.Companion.Update(providerId, memberName, accessToken, refreshToken)), newAttributes)
    }

    fun createOAuthAttributes(providerId: String, registrationId: String, userNameAttributeKey: String, attributes: MutableMap<String, Any>)
            = OAuthAttributes(providerId, registrationId, userNameAttributeKey, attributes)

    fun parseMemberName(attributes: Map<String, Any>): String {
        val account = attributes["kakao_account"] as Map<*, *>
        val profile = account["profile"] as Map<*, *>
        return profile["nickname"] as String
    }

    fun parseProviderId(attributes: Map<String, Any>, nameAttributeKey: String): String {
        return attributes[nameAttributeKey].toString()
    }
}