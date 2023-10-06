package com.ragicorp.whatsthecode.library.libContact.api

import retrofit2.http.GET
import retrofit2.http.Query

interface AddressApiService {
    @GET("api/?limit=10")
    suspend fun searchAddressNoCoordinates(@Query("q") query: String): AddressApiResponse
}