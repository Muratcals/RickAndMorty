package com.example.rickyandmorty.model


import com.google.gson.annotations.SerializedName

data class LocationModelX(
    @SerializedName("results")
    val results: List<Result>
)