package com.miibarra.marsroverphotos.data.remote

import com.miibarra.marsroverphotos.data.remote.responses.RoverData
import com.miibarra.marsroverphotos.data.remote.responses.RoverListData
import com.miibarra.marsroverphotos.data.remote.responses.SolPhotoData
import com.miibarra.marsroverphotos.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RoverRetrofitService {

    @GET("rovers?api_key=$API_KEY")
    suspend fun getRoverList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): RoverListData

    @GET("rovers/{roverName}/photos?sol=1000&api_key=$API_KEY")
    suspend fun getRoverPhotoListBySol(
        @Path("roverName") roverName: String,
        @Query("numSol") numSol: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SolPhotoData

    @GET("rovers/{roverName}?api_key=$API_KEY")
    suspend fun getRoverInformationByName(
        @Path("roverName") roverName: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): RoverData

    @GET("rovers/{roverName}/photos?sol=1000&api_key=$API_KEY")
    suspend fun getPhotoDetail(
        @Path("roverName") roverName: String,
        @Query("id") id: Int
    )
}