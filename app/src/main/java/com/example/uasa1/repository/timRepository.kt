package com.example.uasa1.repository

import com.example.uasa1.API.TimAPI
import com.example.uasa1.model.Tim
import com.example.uasa1.model.TimDetailResponse
import com.example.uasa1.model.TimResponse
import com.example.uasa1.model.TugasResponse
import okio.IOException
import retrofit2.Response

interface TimRepository {
    suspend fun getAllTim(): TimResponse
    suspend fun insertTim(tim: Tim)
    suspend fun updateTim(id_tim: Int, tim: Tim)
    suspend fun deleteTim(id_tim: Int)
    suspend fun getTimById(id_tim: Int): TimDetailResponse
}

class NetworkTimRepository(
    private val timAPI: TimAPI
) : TimRepository {

    override suspend fun getAllTim(): TimResponse {
        return timAPI.getAllTim()
    }

    override suspend fun insertTim(tim: Tim) {
       timAPI.insertTim(tim)
    }

    override suspend fun updateTim(id_tim: Int, tim: Tim) {
        val response: Response<TimResponse> = timAPI.updateTim(id_tim, tim)
        if (!response.isSuccessful) {
            throw IOException("Failed to update Tim. HTTP Status Code: ${response.code()}")
        }
    }

    override suspend fun deleteTim(id_tim: Int) {
        try {
            val response = timAPI.deleteTim(id_tim)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Tim. HTTP Status Code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTimById(id_tim: Int): TimDetailResponse {
        return timAPI.getTimById(id_tim)
    }
}
