package com.example.dnd5emonsterviewer.data.remote

import com.example.dnd5emonsterviewer.data.model.Monster
import com.example.dnd5emonsterviewer.data.model.MonsterList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://www.dnd5eapi.co/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MonsterAPIService {

    @GET("api/monsters")
    suspend fun getMonsterReferences() : MonsterList

    @GET("https://www.dnd5eapi.co/api/monsters/" + "{endpoint}")
    suspend fun getMonster(@Path("endpoint") endpoint: String): Monster

}

object MonsterAPI {
    val retrofitService: MonsterAPIService by lazy { retrofit.create(MonsterAPIService::class.java) }
}