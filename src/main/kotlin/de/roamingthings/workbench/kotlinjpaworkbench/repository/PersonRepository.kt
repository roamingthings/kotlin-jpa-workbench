package de.roamingthings.workbench.kotlinjpaworkbench.repository

import de.roamingthings.workbench.kotlinjpaworkbench.domain.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: JpaRepository<Person, Long> {
    fun findByFirstName(first: String): Person
    fun findByLastName(lastName: String): Person
}
