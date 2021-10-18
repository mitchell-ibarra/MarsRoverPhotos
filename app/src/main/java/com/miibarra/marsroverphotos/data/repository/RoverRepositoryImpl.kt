package com.miibarra.marsroverphotos.data.repository

import android.util.Log
import com.miibarra.marsroverphotos.data.remote.RoverRetrofitService
import com.miibarra.marsroverphotos.data.remote.responses.*
import com.miibarra.marsroverphotos.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class RoverRepositoryImpl @Inject constructor(
    private val api: RoverRetrofitService
) : RoverRepository{

    override suspend fun getRoverList(limit: Int, offset: Int): Resource<RoverListData> {
        val response = try {
            api.getRoverList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    override suspend fun getRoverPhotoListBySol(roverName: String, numSol: Int, limit: Int, offset: Int): Resource<SolPhotoData> {
        val response = try {
            api.getRoverPhotoListBySol(roverName, numSol, limit, offset)
        } catch (e: Exception) {
            Log.e("RoverRepo", "Exception: $e")
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    override suspend fun getRoverInformationByName(roverName: String, limit: Int, offset: Int): Resource<RoverData> {
        val response = try {
            api.getRoverInformationByName(roverName, limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }
}