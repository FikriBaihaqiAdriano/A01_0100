package com.example.uasa1.API

import com.example.uasa1.model.Tugas
import com.example.uasa1.model.TugasDetailResponse
import com.example.uasa1.model.TugasResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TugasAPI {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("tugas")
    suspend fun getAllTugas(): TugasResponse

    @GET("tugas/{id_tugas}")
    suspend fun getTugasById(@Path("id_tugas") id_tugas: Int): TugasDetailResponse

    @POST("tugas")
    suspend fun insertTugas(@Body tugas: Tugas)

    @PUT("tugas/{id_tugas}")
    suspend fun updateTugas(@Path("id_tugas") id_tugas: Int, @Body tugas: Tugas): Response<TugasResponse>

    @DELETE("tugas/{id_tugas}")
    suspend fun deleteTugas(@Path("id_tugas") id_tugas: Int): Response<Void>
}
