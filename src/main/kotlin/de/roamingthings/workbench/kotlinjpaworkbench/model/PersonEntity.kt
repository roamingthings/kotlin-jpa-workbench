package de.roamingthings.workbench.kotlinjpaworkbench.model

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
data class PersonEntity(
        @Id @GeneratedValue
        var id: Long? = null,

        @NotBlank
        var firstName: String,

        @NotNull
        var lastName: String,

        var middleName: String? = null
) {
    @OneToMany(mappedBy = "person", cascade = [(CascadeType.ALL)])
    var addresses: MutableSet<AddressEntity> = mutableSetOf()

    fun addAddress(address: AddressEntity) {
        address.person = this
        addresses.add(address)
    }
}

@Entity
data class AddressEntity(
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
    lateinit var person: PersonEntity
}
