package com.example.uasa1.repository

import com.example.uasa1.API.TugasAPI
import com.example.uasa1.model.Tugas
import com.example.uasa1.model.TugasDetailResponse
import com.example.uasa1.model.TugasResponse
import okio.IOException
import retrofit2.Response

interface TugasRepository {
    suspend fun getAllTugas(): TugasResponse
    suspend fun insertTugas(tugas: Tugas)
    suspend fun updateTugas(id_tugas: Int, tugas: Tugas)
    suspend fun deleteTugas(id_tugas: Int)
    suspend fun getTugasById(id_tugas: Int): TugasDetailResponse
}

class NetworkTugasRepository(
    private val tugasAPI: TugasAPI
) : TugasRepository {

    override suspend fun getAllTugas(): TugasResponse {
        return tugasAPI.getAllTugas()
    }

    override suspend fun insertTugas(tugas: Tugas) {
        tugasAPI.insertTugas(tugas)
    }

    override suspend fun updateTugas(id_tugas: Int, tugas: Tugas) {
        val response: Response<TugasResponse> = tugasAPI.updateTugas(id_tugas, tugas)
        if (!response.isSuccessful) {
            throw IOException("Failed to update Tugas. HTTP Status Code: ${response.code()}")
        }
    }

    override suspend fun deleteTugas(id_tugas: Int) {
        try {
            val response: Response<Void> = tugasAPI.deleteTugas(id_tugas)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Tugas. HTTP Status Code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTugasById(id_tugas: Int): TugasDetailResponse {
        return tugasAPI.getTugasById(id_tugas)
    }
}
