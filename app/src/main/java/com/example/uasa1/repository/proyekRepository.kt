package com.example.uasa1.repository

import com.example.uasa1.API.ProyekAPI
import com.example.uasa1.model.Proyek
import com.example.uasa1.model.ProyekDetailResponse
import com.example.uasa1.model.ProyekResponse
import okio.IOException
import retrofit2.Response

interface ProyekRepository {
    suspend fun getAllProyek(): ProyekResponse
    suspend fun insertProyek(proyek: Proyek)
    suspend fun updateProyek(id_proyek: Int, proyek: Proyek)
    suspend fun deleteProyek(id_proyek: Int)
    suspend fun getProyekById(id_proyek: Int): ProyekDetailResponse
}

class NetworkProyekRepository(
    private val proyekAPI: ProyekAPI
) : ProyekRepository {

    override suspend fun getAllProyek(): ProyekResponse {
        return proyekAPI.getAllProyek()
    }

    override suspend fun insertProyek(proyek: Proyek) {
        proyekAPI.insertProyek(proyek)
    }

    override suspend fun updateProyek(id_proyek: Int, proyek: Proyek) {
        val response: Response<ProyekResponse> = proyekAPI.updateProyek(id_proyek, proyek)
        if (!response.isSuccessful) {
            throw IOException("Failed to update Proyek. HTTP Status Code: ${response.code()}")
        }
    }

    override suspend fun deleteProyek(id_proyek: Int) {
        try {
            val response: Response<Void> = proyekAPI.deleteProyek(id_proyek)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Proyek. HTTP Status Code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getProyekById(id_proyek: Int): ProyekDetailResponse {
        return proyekAPI.getProyekById(id_proyek)
    }
}
