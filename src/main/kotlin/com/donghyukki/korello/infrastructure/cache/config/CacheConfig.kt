package com.donghyukki.korello.infrastructure.cache.config

import com.donghyukki.korello.infrastructure.context.SpringProfile
import io.netty.util.internal.StringUtil
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.CacheKeyPrefix
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
class CacheConfig(
    private val connectionFactory: RedisConnectionFactory,
    private val springProfile: SpringProfile
) : CachingConfigurerSupport() {

    @Bean
    override fun cacheManager(): CacheManager {

        val redisConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .computePrefixWith(CacheKeyPrefix.prefixed(keyPrefix()))
            .entryTtl(Duration.ofMinutes(30L))
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    JdkSerializationRedisSerializer()
                )
            )

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
            .cacheDefaults(redisConfiguration).build()
    }

    private fun keyPrefix() = if (springProfile.active == StringUtil.EMPTY_STRING) StringUtil.EMPTY_STRING
    else "${springProfile.active}::"

}
