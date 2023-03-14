package com.example.rickyandmorty.retrofit

import com.example.rickyandmorty.model.CharacterModel
import com.example.rickyandmorty.model.LocationModelX
import com.example.rickyandmorty.util.constant.searchLocation
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {

    @GET(searchLocation)
    fun getLocation():Single<LocationModelX>

    @GET("character/{id}")
    fun getCharacter(@Path("id") key :Int):Single<CharacterModel>
}