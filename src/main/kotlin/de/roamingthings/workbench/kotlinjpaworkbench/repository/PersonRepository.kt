package de.roamingthings.workbench.kotlinjpaworkbench.repository

import de.roamingthings.workbench.kotlinjpaworkbench.model.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: JpaRepository<PersonEntity, Long> {
    fun findByFirstName(first: String): PersonEntity
    fun findByLastName(lastName: String): PersonEntity
}
