package com.example.uasa1.dependeciesinjection

import com.example.uasa1.API.AnggotaAPI
import com.example.uasa1.API.ProyekAPI
import com.example.uasa1.API.TimAPI
import com.example.uasa1.API.TugasAPI
import com.example.uasa1.repository.NetworkTimRepository
import com.example.uasa1.repository.NetworkTugasRepository
import com.example.uasa1.repository.NetworkAnggotaTimRepository
import com.example.uasa1.repository.TimRepository
import com.example.uasa1.repository.TugasRepository
import com.example.uasa1.repository.AnggotaTimRepository
import com.example.uasa1.repository.NetworkProyekRepository
import com.example.uasa1.repository.ProyekRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val proyekRepository: ProyekRepository
    val timRepository: TimRepository
    val tugasRepository: TugasRepository
    val anggotaTimRepository: AnggotaTimRepository
}

class ManajemenContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:1100/"

    private val json = Json { ignoreUnknownKeys = true  }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val timAPI: TimAPI by lazy { retrofit.create(TimAPI::class.java) }
    private val tugasAPI: TugasAPI by lazy { retrofit.create(TugasAPI::class.java) }
    private val anggotaTimAPI: AnggotaAPI by lazy { retrofit.create(AnggotaAPI::class.java) }
    private val proyekAPI: ProyekAPI by lazy {retrofit.create(ProyekAPI::class.java) }

    override val timRepository: TimRepository by lazy {
        NetworkTimRepository(timAPI)
    }

    override val tugasRepository: TugasRepository by lazy {
        NetworkTugasRepository(tugasAPI)
    }

    override val anggotaTimRepository: AnggotaTimRepository by lazy {
        NetworkAnggotaTimRepository(anggotaTimAPI)
    }

    override val proyekRepository: ProyekRepository by lazy {
        NetworkProyekRepository(proyekAPI)
    }
}
