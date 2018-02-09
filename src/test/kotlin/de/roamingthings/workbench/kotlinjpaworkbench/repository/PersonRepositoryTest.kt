package de.roamingthings.workbench.kotlinjpaworkbench.repository

import de.roamingthings.workbench.kotlinjpaworkbench.domain.Address
import de.roamingthings.workbench.kotlinjpaworkbench.domain.Person
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@DataJpaTest
@ActiveProfiles("test")
class PersonRepositoryIT {
    @Autowired
    lateinit var personRepository: PersonRepository

    @Test
    fun `should persist person`() {
        // given
        val aPerson = Person(name = "Toni Tester")

        // when
        personRepository.saveAndFlush(aPerson)

        // then
        val actualPerson = personRepository.findByName("Toni Tester")
        assertThat(actualPerson).isNotNull()
        assertThat(actualPerson.id).isNotNull()
        assertThat(actualPerson.name).isEqualTo("Toni Tester")
    }

    @Test
    fun `should persist person with address`() {
        // given
        val anAddress = Address(address = "Mustergasse 1")
        val aPerson = Person(name = "Toni Tester")
        aPerson.addAddress(anAddress)

        // when
        personRepository.saveAndFlush(aPerson)

        // then
        val findById = personRepository.findById(aPerson.id!!)
        assertThat(findById.isPresent).isTrue()

        val actualPerson = personRepository.findByName("Toni Tester")
        assertThat(actualPerson).isNotNull()
        assertThat(actualPerson.id).isNotNull()
        assertThat(actualPerson.name).isEqualTo("Toni Tester")
        assertThat(actualPerson.addresses).hasSize(1)
        assertThat(actualPerson.addresses).extracting("person")
                .contains(actualPerson)
    }

    @Test
    fun `should copy person with relation`() {
        // given
        val aPerson = aPersistedPersonWithAddress()
        val anAddress = aPerson.addresses.elementAt(0)

        // when
        val copiedPerson = aPerson.copy()
        copiedPerson.addresses = HashSet(aPerson.addresses)

        // then
        val softly = SoftAssertions()
        softly.assertThat(copiedPerson.name).isEqualTo(aPerson.name)
        softly.assertThat(copiedPerson.addresses).hasSize(aPerson.addresses.size)

        val copiedAddress = aPerson.addresses.elementAt(0)
        assertThat(copiedAddress.address).isEqualTo(anAddress.address)
        softly.assertAll()
    }

    private fun aPersistedPersonWithAddress(): Person {
        val anAddress = Address(address = "Mustergasse 1")

        val aPerson = Person(name = "Toni Tester")
        aPerson.addAddress(anAddress)

        return personRepository.saveAndFlush(aPerson)
    }
}