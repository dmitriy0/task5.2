package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class Hero(
    val name: String,
    val image: Image,
    val appearance: Appearance,
    )


class Image(
    val url: String
    )


class Appearance(
    val gender: String,
    val height: List<String>,
    val weight: List<String>,
    @SerializedName("eye-color")
    val eyeColor: String,
    @SerializedName("hair-color")
    val hairColor: String
)