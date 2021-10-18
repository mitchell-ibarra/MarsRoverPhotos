package com.miibarra.marsroverphotos.data.repository

import com.miibarra.marsroverphotos.data.remote.responses.RoverData
import com.miibarra.marsroverphotos.data.remote.responses.RoverListData
import com.miibarra.marsroverphotos.data.remote.responses.SolPhotoData
import com.miibarra.marsroverphotos.utils.Resource


interface RoverRepository {
    suspend fun getRoverList(limit: Int, offset: Int): Resource<RoverListData>
    suspend fun getRoverPhotoListBySol(roverName: String, numSol: Int, limit: Int, offset: Int): Resource<SolPhotoData>
    suspend fun getRoverInformationByName(roverName: String, limit: Int, offset: Int) : Resource<RoverData>
}