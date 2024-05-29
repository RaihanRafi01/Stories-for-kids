package com.example.stories

import ApiClientTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("stories")
    suspend fun getStories(): List<Story>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://run.mocky.io/v3/c0e9340a-a66e-47bf-8dd2-47a7182d22c5/")  // Replace with your API base URL
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: ApiService = retrofit.create(ApiService::class.java)
//val apiService: ApiService = ApiClientTest()