package com.example.uasa1.API

import com.example.uasa1.model.Anggota
import com.example.uasa1.model.AnggotaDetailResponse
import com.example.uasa1.model.AnggotaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnggotaAPI {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("anggota")
    suspend fun getAllAnggotaTim(): AnggotaResponse

    @GET("anggota/{id_anggota}")
    suspend fun getAnggotaTimById(@Path("id_anggota") id_anggota: Int): AnggotaDetailResponse

    @POST("anggota/anggota")
    suspend fun insertAnggotaTim(@Body anggotaTim: Anggota)

    @PUT("anggota/{id_anggota}")
    suspend fun updateAnggotaTim(@Path("id_anggota") id_anggota: Int, @Body anggotaTim: Anggota): Response<AnggotaResponse>

    @DELETE("anggota/{id_anggota}")
    suspend fun deleteAnggotaTim(@Path("id_anggota") id_anggota: Int): Response<Void>
}
