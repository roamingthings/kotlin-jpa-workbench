package de.roamingthings.workbench.kotlinjpaworkbench

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KotlinJpaWorkbenchApplication

fun main(args: Array<String>) {
    SpringApplication.run(KotlinJpaWorkbenchApplication::class.java, *args)
}
