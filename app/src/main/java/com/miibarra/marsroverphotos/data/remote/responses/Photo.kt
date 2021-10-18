package com.miibarra.marsroverphotos.data.remote.responses

data class Photo(
    val camera: CameraXX,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: RoverXX,
    val sol: Int
)