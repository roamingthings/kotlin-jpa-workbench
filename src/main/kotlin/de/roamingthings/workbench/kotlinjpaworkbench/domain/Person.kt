package de.roamingthings.workbench.kotlinjpaworkbench.domain

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Cacheable(false)
data class Person(
        @Id @GeneratedValue
        val id: Long? = null,

        @NotBlank
        val name: String
) {
    @OneToMany(mappedBy = "person", cascade = [(CascadeType.ALL)])
    var addresses: Set<Address> = HashSet()

    fun addAddress(address: Address) {
        address.person = this
        addresses += address
    }
}

@Entity
@Cacheable(false)
data class Address(
        @Id @GeneratedValue
        val id: Long? = null,

        @NotBlank val address: String
) {
    @NotNull
    @ManyToOne(optional = false)
    lateinit var person: Person
}
