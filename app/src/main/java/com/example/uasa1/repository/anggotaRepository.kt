package com.example.uasa1.repository

import com.example.uasa1.API.AnggotaAPI
import com.example.uasa1.model.Anggota
import com.example.uasa1.model.AnggotaDetailResponse
import com.example.uasa1.model.AnggotaResponse
import okio.IOException
import retrofit2.Response

interface AnggotaTimRepository {
    suspend fun getAllAnggotaTim(): AnggotaResponse
    suspend fun insertAnggotaTim(anggotaTim: Anggota)
    suspend fun updateAnggotaTim(id_anggota: Int, anggotaTim: Anggota)
    suspend fun deleteAnggotaTim(id_anggota: Int)
    suspend fun getAnggotaTimById(id_anggota: Int): AnggotaDetailResponse
}

class NetworkAnggotaTimRepository(
    private val anggotaTimAPI: AnggotaAPI
) : AnggotaTimRepository {

    override suspend fun getAllAnggotaTim(): AnggotaResponse {
        return anggotaTimAPI.getAllAnggotaTim()
    }

    override suspend fun insertAnggotaTim(anggotaTim: Anggota) {
        anggotaTimAPI.insertAnggotaTim(anggotaTim)
    }

    override suspend fun updateAnggotaTim(id_anggota: Int, anggotaTim: Anggota) {
        val response: Response<AnggotaResponse> = anggotaTimAPI.updateAnggotaTim(id_anggota, anggotaTim)
        if (!response.isSuccessful) {
            throw IOException("Failed to update Anggota Tim. HTTP Status Code: ${response.code()}")
        }
    }

    override suspend fun deleteAnggotaTim(id_anggota: Int) {
        try {
            val response: Response<Void> = anggotaTimAPI.deleteAnggotaTim(id_anggota)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Anggota Tim. HTTP Status Code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAnggotaTimById(id_anggota: Int): AnggotaDetailResponse {
        return anggotaTimAPI.getAnggotaTimById(id_anggota)
    }
}
