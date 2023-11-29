package com.ragicorp.whatsthecode.library.libContact.api

import com.google.gson.annotations.SerializedName

data class AddressApiResponse(
    val features: List<FeatureResponse>
)

data class FeatureResponse(
    val geometry: GeometryResponse,
    val properties: PropertiesResponse
)

data class GeometryResponse(
    val coordinates: List<Float>
)

data class PropertiesResponse(
    @SerializedName("housenumber")
    val houseNumber: String?,
    val street: String?,
    val city: String?,
    val name: String?,
    @SerializedName("countrycode")
    val countryCode: String,
    val postcode: String?
)