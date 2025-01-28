package com.example.uasa1.API

import com.example.uasa1.model.Tim
import com.example.uasa1.model.TimDetailResponse
import com.example.uasa1.model.TimResponse
import com.example.uasa1.model.TugasResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TimAPI {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("tim")
    suspend fun getAllTim(): TimResponse

    @GET("tim/{id_tim}")
    suspend fun getTimById(@Path("id_tim") id_tim: Int): TimDetailResponse

    @POST("tim/tim")
    suspend fun insertTim(@Body tim: Tim)

    @PUT("tim/{id_tim}")
    suspend fun updateTim(@Path("id_tim") id_tim: Int, @Body tim: Tim): Response<TimResponse>

    @DELETE("tim/{id_tim}")
    suspend fun deleteTim(@Path("id_tim") id_tim: Int): Response<Void>
}
