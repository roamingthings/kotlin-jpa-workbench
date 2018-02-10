package de.roamingthings.workbench.kotlinjpaworkbench.domain

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Cacheable(false)
data class Person(
        @Id @GeneratedValue
        var id: Long? = null,

        @NotBlank
        var firstName: String,

        @NotNull
        var lastName: String,

        var middleName: String? = null
) {
    @OneToMany(mappedBy = "person", cascade = [(CascadeType.ALL)])
    var addresses: MutableSet<Address> = mutableSetOf()

    fun addAddress(address: Address) {
        address.person = this
        addresses.add(address)
    }
}

@Entity
@Cacheable(false)
data class Address(
        @Id @GeneratedValue
        var id: Long? = null,

        @NotBlank
        var streetAddress: String,

        @NotBlank
        var city: String,

        @NotBlank
        @Size(min = 5, max = 5)
        var postalCode: String,

        var addressAddOn: String? = null
) {
    @NotNull
    @ManyToOne(optional = false)
    lateinit var person: Person
}
