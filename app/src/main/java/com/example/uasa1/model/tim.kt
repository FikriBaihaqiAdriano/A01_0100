package com.example.uasa1.model

import kotlinx.serialization.Serializable

@Serializable
data class Tim (
    val id_tim: Int,
    val nama_tim: String,
    val deskripsi_tim: String
)

@Serializable
data class TimDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Tim
)

@Serializable
data class TimResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tim>
)