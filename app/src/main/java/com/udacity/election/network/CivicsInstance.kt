package com.udacity.election.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.election.network.jsonadapter.ElectionAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object CivicsInstance {
    private const val API_KEY = "AIzaSyBgNc70ArQIheu7SDsTJtiwU-ad5htoRak"
    private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"

    private val moshiInstance = Moshi.Builder()
        .add(ElectionAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun buildOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()
        val url = original
            .url()
            .newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()
        val request = original
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }.build()
    }

    private val retrofitIntstance = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshiInstance))
        .client(buildOkHttpClient())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: CivicsApiService by lazy {
        retrofitIntstance.create(CivicsApiService::class.java)
    }

    suspend fun getElections() = retrofitService.getElections()
    suspend fun getElectionsJsonStr() = retrofitService.getElectionsJsonStr()
    suspend fun getVoterInfo(address: String, id: Int) = retrofitService.getVoterInfo(address, id)
    suspend fun getVoterInfoJsonStr(address: String, id: Int) = retrofitService.getVoterInfoJsonStr(address, id)
    suspend fun getRepresentatives(address: String) = retrofitService.getRepresentatives(address)
    suspend fun getRepresentativesJsonStr(address: String) = retrofitService.getRepresentativesJsonStr(address)
}