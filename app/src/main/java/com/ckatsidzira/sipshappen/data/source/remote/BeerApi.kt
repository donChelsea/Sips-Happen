package com.ckatsidzira.sipshappen.data.source.remote

import com.ckatsidzira.sipshappen.data.source.remote.model.BeerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BeerApi {

    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PER_PAGE,
    ): List<BeerDto>

    @GET("beers/{id}")
    suspend fun getBeerById(@Path("id") id: Int): BeerDto

    companion object {
        const val PER_PAGE = 15
    }
}