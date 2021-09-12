package com.donghyukki.korello.infrastructure.aop

import org.springframework.context.annotation.Profile

@Profile("!local")
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class DisabledLocal()
