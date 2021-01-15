package com.donghyukki.korello.infrastructure.security

data class OAuthAttributes(
    val providerId: String,
    val registrationId: String,
    val nameAttributeKey: String,
    val attributes: Map<String, Any>,
)