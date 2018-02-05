package de.roamingthings.workbench.kotlinjpaworkbench.domain

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class Person(
        @Id @GeneratedValue
        val id: Long? = null,
        @NotBlank val name: String,

        @OneToMany(mappedBy = "person", cascade = [(CascadeType.ALL)])
        var addresses: MutableList<Address> = ArrayList()
)

@Entity
data class Address(
        @Id @GeneratedValue
        val id: Long? = null,

        @NotBlank val address: String,

        @NotNull
        @ManyToOne(optional = false)
        var person: Person
)
