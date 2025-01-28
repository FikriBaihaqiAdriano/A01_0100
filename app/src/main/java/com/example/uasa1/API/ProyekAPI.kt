package com.example.uasa1.API

import com.example.uasa1.model.Proyek
import com.example.uasa1.model.ProyekDetailResponse
import com.example.uasa1.model.ProyekResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProyekAPI {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("proyek")
    suspend fun getAllProyek(): ProyekResponse

    @GET("proyek/{id_proyek}")
    suspend fun getProyekById(@Path("id_proyek") id_proyek: Int): ProyekDetailResponse

    @POST("proyek/proyek")
    suspend fun insertProyek(@Body proyek: Proyek)

    @PUT("proyek/{id_proyek}")
    suspend fun updateProyek(@Path("id_proyek") id_proyek: Int, @Body proyek: Proyek): Response<ProyekResponse>

    @DELETE("proyek/{id_proyek}")
    suspend fun deleteProyek(@Path("id_proyek") id_proyek: Int): Response<Void>
}
