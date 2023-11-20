package com.example.countries.service

import com.example.countries.model.Country
import retrofit2.http.GET

interface CountryApiService {

    @GET("countries.json")
    suspend fun getCountries(): List<Country>
}