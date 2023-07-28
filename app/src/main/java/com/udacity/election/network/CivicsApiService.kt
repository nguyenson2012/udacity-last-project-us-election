package com.udacity.election.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.udacity.election.network.models.ElectionResponse
import com.udacity.election.network.models.RepresentativeResponse
import com.udacity.election.network.models.VoterInfoResponse
/**
 *  Documentation for the Google Civics API Service can be found at https://developers.google.com/civic-information/docs/v2
 */
interface CivicsApiService {
    @GET("elections")
    suspend fun getElections(): ElectionResponse

    @GET("elections")
    suspend fun getElectionsJsonStr(): String

    @GET("voterinfo")
    suspend fun getVoterInfo(
        @Query("address") address: String,
        @Query("electionId") electionId: Int
    ): VoterInfoResponse

    @GET("voterinfo")
    suspend fun getVoterInfoJsonStr(
        @Query("address") address: String,
        @Query("electionId") electionId: Int
    ): String

    @GET("representatives")
    suspend fun getRepresentatives(
        @Query("address") address: String
    ): RepresentativeResponse

    @GET("representatives")
    suspend fun getRepresentativesJsonStr(
        @Query("address") address: String
    ): String
}