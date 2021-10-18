package com.miibarra.marsroverphotos.data.remote.responses

data class RoverXXX(
    val cameras: List<CameraXXX>,
    val id: Int,
    val landing_date: String,
    val launch_date: String,
    val max_date: String,
    val max_sol: Int,
    val name: String,
    val status: String,
    val total_photos: Int
)