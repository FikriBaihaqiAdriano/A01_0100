package com.example.uasa1.model

import kotlinx.serialization.Serializable

@Serializable
data class Tugas(
    val id_tugas: Int,
    val id_proyek: Int,
    val id_tim: Int,
    val nama_tugas: String,
    val deskripsi_tugas: String,
    val prioritas: String,
    val status_tugas: String
)

@Serializable
data class TugasDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Tugas
)

@Serializable
data class TugasResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tugas>
)

