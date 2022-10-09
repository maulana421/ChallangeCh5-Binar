package com.solanacode.challangech5.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    val name: String,
    val image: String,
    val director: String,
    val description: String
): Serializable