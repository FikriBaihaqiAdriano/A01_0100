package com.example.uasa1.model

import kotlinx.serialization.Serializable

@Serializable
data class Anggota(
    val id_anggota: Int,
    val id_tim: Int,
    val nama_anggota: String,
    val peran: String,
)

@Serializable
data class AnggotaDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Anggota
)

@Serializable
data class AnggotaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Anggota>
)