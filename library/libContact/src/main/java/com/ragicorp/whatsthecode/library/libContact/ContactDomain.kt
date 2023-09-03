package com.ragicorp.whatsthecode.library.libContact

import java.util.UUID

data class ContactDomain(
    val id: UUID,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val codes: List<Pair<String, String>>,
    val apartmentDescription: String,
    val freeText: String,
    val color: Int
)

val stubContact = ContactDomain(
    id = UUID.randomUUID(),
    name = "James Bond",
    phoneNumber = "007",
    address = "MI6",
    codes = listOf(Pair("Code name", "007"), Pair("Number of movies", "Too many")),
    apartmentDescription = "Something with many expensive items",
    freeText = "Best secret agent",
    color = 1
)