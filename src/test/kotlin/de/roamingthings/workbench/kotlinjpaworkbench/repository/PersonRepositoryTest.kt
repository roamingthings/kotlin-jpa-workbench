package de.roamingthings.workbench.kotlinjpaworkbench.repository

import de.roamingthings.workbench.kotlinjpaworkbench.domain.Address
import de.roamingthings.workbench.kotlinjpaworkbench.domain.Person
import org.assertj.core.api.Assertions.assertThat
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
        val aPerson = Person(name = "Toni Tester")
        val anAddress = Address(address = "Mustergasse 1", person = aPerson)
        aPerson.addresses.add(anAddress)

        // when
        personRepository.saveAndFlush(aPerson)

        // then
        val actualPerson = personRepository.findByName("Toni Tester")
        assertThat(actualPerson).isNotNull()
        assertThat(actualPerson.id).isNotNull()
        assertThat(actualPerson.name).isEqualTo("Toni Tester")
        assertThat(actualPerson.addresses).hasSize(1)
        assertThat(actualPerson.addresses).extracting("person")
                .contains(actualPerson)
    }
}