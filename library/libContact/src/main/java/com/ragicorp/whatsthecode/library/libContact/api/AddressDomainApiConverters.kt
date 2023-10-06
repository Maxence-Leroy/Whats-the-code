package com.ragicorp.whatsthecode.library.libContact.api

import com.google.i18n.addressinput.common.AddressData
import com.google.i18n.addressinput.common.FormOptions
import com.google.i18n.addressinput.common.FormatInterpreter


internal object AddressDomainApiConverters {
    internal fun addressResultFromApi(result: AddressApiResponse): List<String> =
        result.features.map { feature ->
            var address = ""

            val name = feature.properties.name
            if (name?.isNotBlank() == true) {
                address += "$name\n"
            }
            val number = feature.properties.houseNumber
            if (number?.isNotBlank() == true) {
                address += "$number "
            }
            val street = feature.properties.street
            if (street?.isNotBlank() == true) {
                address += street
            }

            val addressData = AddressData.Builder()
                .setAddress(address)
                .setLocality(feature.properties.city)
                .setPostalCode(feature.properties.postcode)
                .setCountry(feature.properties.countryCode)
                .build()

            val formatInterpreter = FormatInterpreter(FormOptions().createSnapshot())

            val addressFragments = formatInterpreter.getEnvelopeAddress(addressData)

            addressFragments.joinToString("\n")
        }
}