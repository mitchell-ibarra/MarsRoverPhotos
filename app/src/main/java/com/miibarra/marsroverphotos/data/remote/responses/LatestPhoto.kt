package com.miibarra.marsroverphotos.data.remote.responses

data class LatestPhoto(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
)