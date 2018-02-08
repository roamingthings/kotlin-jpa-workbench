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
    var addresses: MutableSet<Address> = HashSet()
}

@Entity
@Cacheable(false)
data class Address(
        @Id @GeneratedValue
        val id: Long? = null,

        @NotBlank val address: String
) {
    constructor(address: String, person: Person): this(address = address) {
        this.person = person
    }

    @NotNull
    @ManyToOne(optional = false)
    lateinit var person: Person
}
