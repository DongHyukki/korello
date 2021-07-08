package com.donghyukki.korello.archunit

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


@AnalyzeClasses(packages = ["com.donghyukki.korello"])
internal class KorelloArchTest {

    @DisplayName("Service 패키지는 Controller 패키지에서만 접근 가능하다.")
    @ArchTest
    fun check_rule_as_service_dependency_by_package(importedClasses: JavaClasses) {
        val rule = classes().that()
                            .resideInAPackage("..service..")
                            .should()
                            .onlyHaveDependentClassesThat()
                            .resideInAnyPackage("..controller..")

        rule.check(importedClasses)
    }

    @DisplayName("Service로 끝나는 클래스는 Controller 클래스에서만 접근 가능하다.")
    @ArchTest
    fun check_rule_as_service_dependency_by_class(importedClasses: JavaClasses) {
        val rule = classes().that()
                            .haveNameMatching(".*Service")
                            .should()
                            .onlyBeAccessed()
                            .byClassesThat()
                            .haveNameMatching(".*Controller")

        rule.check(importedClasses)
    }

    @DisplayName("Repository로 끝나는 클래스는 Service 클래스에서만 접근 가능하다.")
    @ArchTest
    fun check_rule_as_repository_dependency_by_class(importedClasses: JavaClasses) {
        val rule = classes().that()
                            .haveNameMatching(".*Repository")
                            .should()
                            .onlyBeAccessed()
                            .byClassesThat()
                            .haveNameMatching(".*Service")

        rule.check(importedClasses)
    }

    @DisplayName("Repository Annoation이 붙은 클래스는 Service Annotation이 붙은 클래스 에서만 접근 가능하다.")
    @ArchTest
    fun check_rule_as_repository_dependency_by_annotation(importedClasses: JavaClasses) {
        val rule = classes().that()
                            .areAnnotatedWith(Repository::class.java)
                            .should()
                            .onlyHaveDependentClassesThat()
                            .areAnnotatedWith(Service::class.java)

        rule.check(importedClasses)
    }

    @DisplayName("순환 참조가 있는지 확인한다.")
    @ArchTest
    fun check_rule_as_dependency_cycle(importedClasses: JavaClasses) {
        val rule = slices().matching("com.donghyukki.korello")
                           .should().beFreeOfCycles()

        rule.check(importedClasses)
    }

    @DisplayName("@Autowired 은 사용하지 말아야 한다.")
    @ArchTest
    fun check_rule_as_autowried_annotation(importedClasses: JavaClasses) {
        val rule = members().should()
                            .notBeAnnotatedWith(Autowired::class.java)

        rule.check(importedClasses)
    }

    @DisplayName("헥사고날 아키텍처 검증")
    @ArchTest
    fun check_rule_as_layered_arch_check(importedClasses: JavaClasses) {

        layeredArchitecture()
            .layer("Infrastructure").definedBy("..infrastructure..")
            .layer("Application").definedBy("..application..")
            .layer("Presentation").definedBy("..presentation..")
            .layer("Domain").definedBy("..domain..")

            .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
            .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
            .whereLayer("Application").mayOnlyBeAccessedByLayers("Presentation")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application")
            .check(importedClasses)
    }

}