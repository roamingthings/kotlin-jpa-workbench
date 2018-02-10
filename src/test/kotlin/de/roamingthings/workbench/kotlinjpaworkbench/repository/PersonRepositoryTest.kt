package de.roamingthings.workbench.kotlinjpaworkbench.repository

import de.roamingthings.workbench.kotlinjpaworkbench.domain.Address
import de.roamingthings.workbench.kotlinjpaworkbench.domain.Person
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.JUnitSoftAssertions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


/*
 * You could say that these tests are basically testing the `EntityManager`.
 * However the aim is to play with the implementation of various aspects regarding the JPA Entities.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@DataJpaTest
@ActiveProfiles("test")
class PersonRepositoryIT {
    @Autowired
    lateinit var personRepository: PersonRepository

    @get: Rule
    val softly = JUnitSoftAssertions()

    @Test
    fun `should persist person`() {
        // given
        val aPerson = aPerson()

        // when
        personRepository.saveAndFlush(aPerson)

        // then
        val actualPerson = personRepository.findByFirstName("Toni")

        softly.assertThat(actualPerson).isNotNull()
        softly.assertThat(actualPerson.id).isNotNull()
        softly.assertThat(actualPerson.firstName).isEqualTo("Toni")
        softly.assertThat(actualPerson.lastName).isEqualTo("Tester")
    }

    @Test
    fun `should persist person with address`() {
        // given
        val aPerson = aPerson()
        val anAddress = anAddress()
        aPerson.addAddress(anAddress)

        // when
        personRepository.saveAndFlush(aPerson)

        // then
        val findById = personRepository.findById(aPerson.id!!)
        assertThat(findById.isPresent).isTrue()

        val actualPerson = personRepository.findByFirstName("Toni")
        assertThat(actualPerson).isNotNull()
        assertThat(actualPerson.id).isNotNull()
        assertThat(actualPerson.firstName).isEqualTo("Toni")
        assertThat(actualPerson.lastName).isEqualTo("Tester")
        assertThat(actualPerson.addresses).hasSize(1)
        assertThat(actualPerson.addresses).extracting("person")
                .contains(actualPerson)
    }

    @Test
    fun `should update person without modifying address`() {
        // given
        val aPerson = aPersistedPersonWithAddress()

        // when
        aPerson.lastName = "Modified"
        personRepository.saveAndFlush(aPerson)
        val actualPerson = personRepository.findByLastName("Modified")

        // then
        softly.assertThat(actualPerson).isNotNull()
        softly.assertThat(actualPerson.id).isNotNull()
        softly.assertThat(actualPerson.firstName).isEqualTo("Toni")
        softly.assertThat(actualPerson.lastName).isEqualTo("Modified")
        softly.assertThat(actualPerson.addresses).hasSize(1)
        softly.assertThat(actualPerson.addresses).extracting("streetAddress")
                .contains("Mustergasse 1")
        softly.assertThat(actualPerson.addresses).extracting("person")
                .contains(actualPerson)
    }

    private fun aPersistedPersonWithAddress(): Person {
        val personToPersist = aPerson()
        personToPersist.addAddress(anAddress())

        return personRepository.saveAndFlush(personToPersist)
    }

    private fun anAddress(): Address {
        return Address(
                streetAddress = "Mustergasse 1",
                city = "Berlin",
                postalCode = "12345"
        )
    }

    private fun aPerson() = Person(firstName = "Toni", lastName = "Tester")
}